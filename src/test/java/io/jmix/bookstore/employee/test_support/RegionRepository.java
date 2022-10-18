package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RegionRepository implements EntityRepository<RegionData, Region> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    RegionMapper mapper;

    @Override
    public Region save(RegionData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
