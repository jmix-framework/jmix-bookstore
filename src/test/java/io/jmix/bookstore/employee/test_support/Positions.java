package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Positions
        implements TestDataProvisioning<PositionData, PositionData.PositionDataBuilder, Position> {

    @Autowired
    PositionRepository repository;

    public static String DEFAULT_NAME = "position_name";
    @Autowired
    private PositionMapper mapper;

    @Override
    public PositionData.PositionDataBuilder defaultData() {
        return PositionData.builder()
                .name(DEFAULT_NAME + "-" + UUID.randomUUID());
    }

    @Override
    public Position save(PositionData productCategoryData) {
        return repository.save(productCategoryData);
    }

    @Override
    public Position create(PositionData productCategoryData) {
        return mapper.toEntity(productCategoryData);
    }

    @Override
    public Position createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Position saveDefault() {
        return save(defaultData().build());
    }
}
