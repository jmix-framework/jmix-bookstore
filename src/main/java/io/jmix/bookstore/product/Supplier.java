package io.jmix.bookstore.product;

import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.StandardEntity;
import io.jmix.bookstore.entity.Title;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_SUPPLIER")
@Entity(name = "bookstore_Supplier")
public class Supplier extends StandardEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "ADDRESS_STREET", nullable = false)),
            @AttributeOverride(name = "postCode", column = @Column(name = "ADDRESS_POST_CODE")),
            @AttributeOverride(name = "city", column = @Column(name = "ADDRESS_CITY"))
    })
    private Address address;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_TITLE")
    private String contactTitle;

    @Column(name = "PHONE")
    private String phone;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WEBSITE")
    private String website;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Title getContactTitle() {
        return contactTitle == null ? null : Title.fromId(contactTitle);
    }

    public void setContactTitle(Title contactTitle) {
        this.contactTitle = contactTitle == null ? null : contactTitle.getId();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
