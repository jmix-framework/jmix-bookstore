package io.jmix.bookstore.fulfillment.test_support;

import io.jmix.bookstore.employee.test_support.Regions;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.test_support.AddressData;
import io.jmix.bookstore.entity.test_support.AddressMapper;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FulfillmentCenters
        implements TestDataProvisioning<FulfillmentCenterData, FulfillmentCenterData.FulfillmentCenterDataBuilder, FulfillmentCenter> {

    @Autowired
    FulfillmentCenterRepository repository;

    public static String DEFAULT_NAME = "fulfillment_center_name";
    public static final String DEFAULT_STREET = "street";
    public static final String DEFAULT_POST_CODE = "postcode";
    public static final String DEFAULT_CITY = "city";
    public static final String DEFAULT_STATE = "state";
    public static final String DEFAULT_COUNTRY = "country";
    public static final Point DEFAULT_POSITION = new GeometryFactory().createPoint(new Coordinate(-73.9905353, 40.7484404));
    @Autowired
    private FulfillmentCenterMapper mapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private Regions regions;

    @Override
    public FulfillmentCenterData.FulfillmentCenterDataBuilder defaultData() {
        return FulfillmentCenterData.builder()
                .name(DEFAULT_NAME + "-" + UUID.randomUUID())
                .region(regions.createDefault())
                .address(defaultAddress());
    }

    public Address defaultAddress() {
        return addressMapper.toEntity(AddressData.builder()
                .street(DEFAULT_STREET)
                .postCode(DEFAULT_POST_CODE)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .country(DEFAULT_COUNTRY)
                .position(DEFAULT_POSITION)
                .build());
    }
    @Override
    public FulfillmentCenter save(FulfillmentCenterData productCategoryData) {
        return repository.save(productCategoryData);
    }

    @Override
    public FulfillmentCenter create(FulfillmentCenterData productCategoryData) {
        return mapper.toEntity(productCategoryData);
    }

    @Override
    public FulfillmentCenter createDefault() {
        return create(defaultData().build());
    }

    @Override
    public FulfillmentCenter saveDefault() {
        return save(defaultData().build());
    }
}
