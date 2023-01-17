package io.jmix.bookstore.listener;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.directions.AddressInformation;
import io.jmix.bookstore.directions.DirectionsProvider;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_CustomerAddressToRegionLinking")
public class CustomerAddressToRegionLinking {
    private final DirectionsProvider directionsProvider;
    private final DataManager dataManager;

    public CustomerAddressToRegionLinking(DataManager dataManager, DirectionsProvider directionsProvider) {
        this.dataManager = dataManager;
        this.directionsProvider = directionsProvider;
    }

    @EventListener
    public void onCustomerSaving(EntitySavingEvent<Customer> event) {

        AvailableTerritories territories = availableTerritories();

        if (event.isNewEntity()) {
            Customer customer = event.getEntity();
            if (customer.getAddress().getPosition() == null){
                directionsProvider.forwardGeocoding(AddressInformation.fromAddress(customer.getAddress()))
                        .ifPresent(position -> {
                            customer.getAddress().setPosition(position);
                            territories.findTerritoryForPosition(position)
                                    .map(Territory::getRegion)
                                    .ifPresent(customer::setAssociatedRegion);
                        });
            }
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
