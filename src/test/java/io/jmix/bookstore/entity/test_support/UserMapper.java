package io.jmix.bookstore.entity.test_support;

import io.jmix.bookstore.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface UserMapper {

    User toEntity(UserData userData);
}
