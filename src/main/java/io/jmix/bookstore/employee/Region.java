package io.jmix.bookstore.employee;

import io.jmix.bookstore.entity.StandardEntity;
import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_REGION")
@Entity(name = "bookstore_Region")
public class Region extends StandardTenantEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
