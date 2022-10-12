package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.test_support.RegionData;
import io.jmix.bookstore.employee.test_support.RegionMapper;
import io.jmix.bookstore.employee.test_support.RegionRepository;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Regions
        implements TestDataProvisioning<RegionData, RegionData.RegionDataBuilder, Region> {

    @Autowired
    RegionRepository repository;

    public static String DEFAULT_NAME = "region_name";
    @Autowired
    private RegionMapper mapper;

    @Override
    public RegionData.RegionDataBuilder defaultData() {
        return RegionData.builder()
                .name(DEFAULT_NAME + "-" + UUID.randomUUID());
    }

    @Override
    public Region save(RegionData productCategoryData) {
        return repository.save(productCategoryData);
    }

    @Override
    public Region create(RegionData productCategoryData) {
        return mapper.toEntity(productCategoryData);
    }

    @Override
    public Region createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Region saveDefault() {
        return save(defaultData().build());
    }
}
