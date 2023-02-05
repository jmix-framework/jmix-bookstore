package io.jmix.bookstore.customer;

import io.jmix.bookstore.directions.AddressInformation;
import io.jmix.bookstore.directions.Geocoding;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.AddressPositionUpdate;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_CustomerAddressUpdate")
public class CustomerAddressUpdate {
    private final Geocoding geocoding;
    private final DataManager dataManager;
    private final AddressPositionUpdate addressPositionUpdate;

    public CustomerAddressUpdate(DataManager dataManager, Geocoding geocoding, AddressPositionUpdate addressPositionUpdate) {
        this.dataManager = dataManager;
        this.geocoding = geocoding;
        this.addressPositionUpdate = addressPositionUpdate;
    }

    public void updateCustomerAddress(Customer customer) {
        Address address = customer.getAddress();

        addressPositionUpdate
                .updateAddressPosition(address);

        if (address.getPosition() != null) {
            AvailableTerritories territories = availableTerritories();
            territories.findTerritoryForPosition(address.getPosition())
                    .map(Territory::getRegion)
                    .ifPresent(customer::setAssociatedRegion);
        }
    }


    private AvailableTerritories availableTerritories() {
        List<Territory> territories = dataManager.load(Territory.class)
                .all()
                .fetchPlan(territory -> {
                    territory.addFetchPlan(FetchPlan.BASE);
                    territory.add("region", FetchPlan.BASE);
                })
                .list();

        return new AvailableTerritories(territories);
    }
}
