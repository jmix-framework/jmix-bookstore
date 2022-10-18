package io.jmix.bookstore.customer;

import io.jmix.bookstore.product.supplier.Address;
import io.jmix.bookstore.product.supplier.StandardTenantEntity;
import io.jmix.bookstore.order.Order;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@JmixEntity
@Table(name = "BOOKSTORE_CUSTOMER")
@Entity(name = "bookstore_Customer")
public class Customer extends StandardTenantEntity {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "ADDRESS_STREET")),
            @AttributeOverride(name = "postCode", column = @Column(name = "ADDRESS_POST_CODE")),
            @AttributeOverride(name = "city", column = @Column(name = "ADDRESS_CITY"))
    })
    private Address address;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @JmixProperty
    @DependsOnProperties({"firstName", "lastName"})
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    @InstanceName
    @DependsOnProperties({"firstName", "lastName"})
    public String getInstanceName() {
        return getFullName();
    }
}
