package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TerritoryRepository implements EntityRepository<TerritoryData, Territory> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    TerritoryMapper mapper;

    @Override
    public Territory save(TerritoryData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
