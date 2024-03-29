package io.jmix.bookstore.entity.test_support;

import io.jmix.core.DataManager;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JmixEntityFactory {

    @Autowired
    DataManager dataManager;

     public <T> T createEntity(@TargetType Class<T> entityClass) {
         return dataManager.create(entityClass);
     }
}
