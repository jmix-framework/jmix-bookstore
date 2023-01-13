package io.jmix.bookstore.test_data.data_provider.fulfillment_center;

import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bookstore.test_data.data_provider.region.AvailableRegions;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("bookstore_FulfillmentCenterDataProvider")
public class FulfillmentCenterDataProvider implements TestDataProvider<FulfillmentCenter, FulfillmentCenterDataProvider.DataContext> {

    protected final DataManager dataManager;

    public record DataContext(AvailableRegions availableRegions){}
    public FulfillmentCenterDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<FulfillmentCenter> create(DataContext dataContext) {
        return commit(createFulfillmentCenters(dataContext.availableRegions()));
    }

    private List<FulfillmentCenter> createFulfillmentCenters(AvailableRegions availableRegions) {
        return AvailableFulfillmentCenters.all()
                .map(entry -> toFulfillmentCenter(entry, availableRegions))
                .collect(Collectors.toList());
    }

    private FulfillmentCenter toFulfillmentCenter(AvailableFulfillmentCenters.Entry entry, AvailableRegions availableRegions) {
        FulfillmentCenter fulfillmentCenter = dataManager.create(FulfillmentCenter.class);
        fulfillmentCenter.setName(entry.getName());
        Address address = dataManager.create(Address.class);
        address.setStreet(entry.getStreet());
        address.setPostCode(entry.getPostCode());
        address.setCity(entry.getCity());
        address.setPosition(entry.getPoint());
        fulfillmentCenter.setAddress(address);
        fulfillmentCenter.setRegion(availableRegions.find(entry.getRegion()));
        return fulfillmentCenter;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        saveContext.setDiscardSaved(true);
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}
