package io.jmix.bookstore.employee;

import io.jmix.bookstore.entity.StandardEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_POSITION")
@Entity(name = "bookstore_Position")
public class Position extends StandardEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "COLOR")
    private String color;

    public PositionColor getColor() {
        return color == null ? null : PositionColor.fromId(color);
    }

    public void setColor(PositionColor color) {
        this.color = color == null ? null : color.getId();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
