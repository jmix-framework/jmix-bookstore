package io.jmix.bookstore.order;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.core.DeletePolicy;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@JmixEntity
@Table(name = "BOOKSTORE_ORDER", indexes = {
        @Index(name = "IDX_ORDER_CUSTOMER_ID", columnList = "CUSTOMER_ID"),
        @Index(name = "IDX_BOOKSTORE_ORDER_FULFILLED_BY", columnList = "FULFILLED_BY_ID")
})
@Entity(name = "bookstore_Order")
public class Order extends StandardTenantEntity {
    @NotNull
    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDate orderDate;

    @NotNull
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer customer;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

    @Column(name = "SHIPPING_DATE")
    private LocalDate shippingDate;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "SHIPPING_ADDRESS_STREET", nullable = false)),
            @AttributeOverride(name = "postCode", column = @Column(name = "SHIPPING_ADDRESS_POST_CODE")),
            @AttributeOverride(name = "city", column = @Column(name = "SHIPPING_ADDRESS_CITY")),
            @AttributeOverride(name = "position", column = @Column(name = "SHIPPING_ADDRESS_POSITION_")),
            @AttributeOverride(name = "state", column = @Column(name = "SHIPPING_ADDRESS_STATE")),
            @AttributeOverride(name = "country", column = @Column(name = "SHIPPING_ADDRESS_COUNTRY"))
    })
    private Address shippingAddress;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    private String status;

    @JoinColumn(name = "FULFILLED_BY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FulfillmentCenter fulfilledBy;

    public FulfillmentCenter getFulfilledBy() {
        return fulfilledBy;
    }

    public void setFulfilledBy(FulfillmentCenter fulfilledBy) {
        this.fulfilledBy = fulfilledBy;
    }

    public OrderStatus getStatus() {
        return status == null ? null : OrderStatus.fromId(status);
    }

    public void setStatus(OrderStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDate shippedDate) {
        this.shippingDate = shippedDate;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @InstanceName
    @DependsOnProperties({"customer", "orderDate"})
    public String getInstanceName(MetadataTools metadataTools, DatatypeFormatter datatypeFormatter) {
        return String.format("%s - %s", metadataTools.getInstanceName(customer), datatypeFormatter.formatLocalDate(orderDate));
    }
}
