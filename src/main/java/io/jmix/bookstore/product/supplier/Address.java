package io.jmix.bookstore.product.supplier;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import static com.google.common.base.Strings.nullToEmpty;

@Embeddable
@JmixEntity(name = "bookstore_Address")
public class Address {
    @NotBlank
    @Column(name = "STREET", nullable = false)
    private String street;

    @Column(name = "POST_CODE")
    private String postCode;

    @Column(name = "CITY")
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @InstanceName
    @DependsOnProperties({"street", "postCode", "city"})
    public String getInstanceName() {
        return String.format("%s\n%s %s", nullToEmpty(street), nullToEmpty(postCode), nullToEmpty(city));
    }
}
