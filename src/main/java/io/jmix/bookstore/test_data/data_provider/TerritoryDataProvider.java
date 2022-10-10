package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.Address;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_TerritoryDataProvider")
public class TerritoryDataProvider implements TestDataProvider<Territory, TerritoryDataProvider.Dependencies> {

    public record Dependencies(List<io.jmix.bookstore.employee.Region> regions){}

    protected final DataManager dataManager;

    public TerritoryDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Territory> create(int amount, Dependencies dependencies) {
        return commit(createTerritories(amount, dependencies.regions()));
    }

    private List<Territory> createTerritories(int amount, List<Region> regions) {
        Faker faker = new Faker();

        return Stream.generate(faker::address).limit(amount)
                .map(address -> toTerritory(address, regions))
                .collect(Collectors.groupingBy(Territory::getName))
                .values().stream()
                .map(products -> products.get(0))
                .collect(Collectors.toList());
    }

    private  Territory toTerritory(Address address, List<Region> regions) {
        Territory territory = dataManager.create(Territory.class);
        territory.setName(address.state());
        territory.setRegion(randomOfList(regions));
        return territory;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}
