package io.jmix.bookstore.entity;

import io.jmix.bookstore.directions.AddressInformation;
import io.jmix.bookstore.directions.Geocoding;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_AddressPositionUpdate")
public class AddressPositionUpdate {
    private final Geocoding geocoding;

    public AddressPositionUpdate(Geocoding geocoding) {
        this.geocoding = geocoding;
    }

    public void updateAddressPosition(Address address) {
        geocoding.forwardGeocoding(AddressInformation.fromAddress(address))
                .ifPresent(address::setPosition);
    }
}
