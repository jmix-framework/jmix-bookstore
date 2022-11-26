package io.jmix.bookstore.customer;

import com.google.common.base.Strings;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.bookstore.order.Order;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.*;
import io.jmix.maps.Geometry;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

import static com.google.common.base.Strings.nullToEmpty;

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
            @AttributeOverride(name = "city", column = @Column(name = "ADDRESS_CITY")),
            @AttributeOverride(name = "position", column = @Column(name = "ADDRESS_POSITION_")),
            @AttributeOverride(name = "state", column = @Column(name = "ADDRESS_STATE")),
            @AttributeOverride(name = "country", column = @Column(name = "ADDRESS_COUNTRY"))
    })
    private Address address;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @Geometry
    @JmixProperty
    public Point getGeometry() {
        return address != null? address.getPosition() : null;
    }
    @DependsOnProperties({"firstName", "lastName"})
    @JmixProperty
    public String getFullName() {
        return String.format("%s %s", nullToEmpty(firstName), nullToEmpty(lastName));
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
        return String.format("%s %s", firstName, lastName);
    }
}
