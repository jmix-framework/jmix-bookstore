package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.PositionColor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionData {
    String name;
    String description;
    String code;
    PositionColor color;
}
