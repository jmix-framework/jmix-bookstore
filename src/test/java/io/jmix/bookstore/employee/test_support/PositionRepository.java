package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PositionRepository implements EntityRepository<PositionData, Position> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    PositionMapper mapper;

    @Override
    public Position save(PositionData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
