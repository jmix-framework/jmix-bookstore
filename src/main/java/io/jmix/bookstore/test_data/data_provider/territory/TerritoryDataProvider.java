package io.jmix.bookstore.test_data.data_provider.territory;

import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bookstore.test_data.data_provider.region.AvailableRegions;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("bookstore_TerritoryDataProvider")
public class TerritoryDataProvider implements TestDataProvider<Territory, TerritoryDataProvider.DataContext> {

    protected final DataManager dataManager;

    public record DataContext(AvailableRegions availableRegions){}
    public TerritoryDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Territory> create(DataContext dataContext) {
        return commit(createTerritories(dataContext.availableRegions()));
    }

    private List<Territory> createTerritories(AvailableRegions availableRegions) {
        return AvailableTerritories.all()
                .map(entry -> toTerritory(entry,availableRegions))
                .collect(Collectors.toList());
    }

    private Territory toTerritory(AvailableTerritories.Entry availableTerritory, AvailableRegions availableRegions) {
        Territory territory = dataManager.create(Territory.class);
        territory.setName(availableTerritory.getName());
        territory.setRegion(availableRegions.find(availableTerritory.getRegion()));
        territory.setGeographicalArea(availableTerritory.getGeographicalArea());
        return territory;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}
