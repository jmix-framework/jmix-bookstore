package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface PositionMapper {

    Position toEntity(PositionData dto);
}
