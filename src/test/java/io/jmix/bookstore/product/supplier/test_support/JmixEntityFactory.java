package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.product.supplier.StandardEntity;
import io.jmix.core.DataManager;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JmixEntityFactory {

    @Autowired
    DataManager dataManager;

     public <T extends StandardEntity> T createEntity(@TargetType Class<T> entityClass) {
         return dataManager.create(entityClass);
     }
}
