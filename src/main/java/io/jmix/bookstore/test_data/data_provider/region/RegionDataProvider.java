package io.jmix.bookstore.test_data.data_provider.region;

import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("bookstore_RegionDataProvider")
public class RegionDataProvider implements TestDataProvider<Region, RegionDataProvider.DataContext> {

    protected final DataManager dataManager;

    public record DataContext(){}
    public RegionDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Region> create(DataContext dataContext) {
        return commit(createRegions());
    }

    private List<Region> createRegions() {
        return AvailableRegions.all()
                .map(this::toRegion)
                .collect(Collectors.toList());
    }

    private Region toRegion(AvailableRegions.Entry entry) {
        Region region = dataManager.create(Region.class);
        region.setName(entry.getName());
        return region;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}
