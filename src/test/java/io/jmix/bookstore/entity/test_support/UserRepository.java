package io.jmix.bookstore.entity.test_support;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserRepository implements EntityRepository<UserData, User> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    UserMapper mapper;

    @Override
    public User save(UserData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
