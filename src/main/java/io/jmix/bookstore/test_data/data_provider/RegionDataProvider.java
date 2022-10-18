package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.employee.Region;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("bookstore_RegionDataProvider")
public class RegionDataProvider implements TestDataProvider<Region, RegionDataProvider.Dependencies> {

    protected final DataManager dataManager;

    public record Dependencies(){}
    public RegionDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Region> create(int amount, Dependencies dependencies) {
        return commit(createRegions(amount));
    }

    private List<Region> createRegions(int amount) {
        Faker faker = new Faker();

        return Stream.generate(faker::address).limit(amount)
                .map(this::toRegion)
                .collect(Collectors.groupingBy(Region::getName))
                .values().stream()
                .map(productCategories -> productCategories.get(0))
                .collect(Collectors.toList());
    }

    private Region toRegion(Address address) {
        Region region = dataManager.create(Region.class);
        region.setName(address.country());
        return region;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}
