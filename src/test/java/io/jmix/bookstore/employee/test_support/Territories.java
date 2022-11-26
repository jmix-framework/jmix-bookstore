package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Territories
        implements TestDataProvisioning<TerritoryData, TerritoryData.TerritoryDataBuilder, Territory> {

    @Autowired
    TerritoryRepository repository;

    public static String DEFAULT_NAME = "territory_name";
    @Autowired
    private TerritoryMapper mapper;
    @Autowired
    private Regions regions;

    @Override
    public TerritoryData.TerritoryDataBuilder defaultData() {
        return TerritoryData.builder()
                .name(DEFAULT_NAME + "-" + UUID.randomUUID())
                .region(regions.createDefault())
                .geographicalArea(new GeometryFactory().createPolygon());
    }

    @Override
    public Territory save(TerritoryData productCategoryData) {
        return repository.save(productCategoryData);
    }

    @Override
    public Territory create(TerritoryData productCategoryData) {
        return mapper.toEntity(productCategoryData);
    }

    @Override
    public Territory createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Territory saveDefault() {
        return save(defaultData().build());
    }
}
