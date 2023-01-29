package io.jmix.bookstore.entity.test_support;

import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.apache.commons.lang3.NotImplementedException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Addresses
        implements TestDataProvisioning<AddressData, AddressData.AddressDataBuilder, Address> {

    private static final String POST_CODE = "12345";
    private static final String CITY = "City";
    private static final String STREET = "Street";
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressData.AddressDataBuilder defaultData() {
        return AddressData.builder()
                .city(CITY)
                .street(STREET)
                .postCode(POST_CODE)
                .position(new GeometryFactory().createPoint(new Coordinate( 0,0)));
    }


    @Override
    public Address save(AddressData addressData)  {
        throw new IllegalStateException("Address cannot be saved explicitly as it is an embedded entity");
    }

    @Override
    public Address create(AddressData addressData) {
        return addressMapper.toEntity(addressData);
    }

    @Override
    public Address createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Address saveDefault() {
        return save(defaultData().build());
    }

}
