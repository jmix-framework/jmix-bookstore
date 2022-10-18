package io.jmix.bookstore.employee;

import io.jmix.bookstore.entity.StandardEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@JmixEntity
@Table(name = "BOOKSTORE_TERRITORY", indexes = {
        @Index(name = "IDX_BOOKSTORE_TERRITORY_REGION", columnList = "REGION_ID")
})
@Entity(name = "bookstore_Territory")
public class Territory extends StandardEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @JoinColumn(name = "REGION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;
    @JoinTable(name = "BOOKSTORE_EMPLOYEE_TERRITORIES",
            joinColumns = @JoinColumn(name = "TERRITORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @ManyToMany
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
