package io.jmix.bookstore.order;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.bookstore.product.Product;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_ORDER_LINE", indexes = {
        @Index(name = "IDX_ORDERLINE_ORDER_ID", columnList = "ORDER_ID"),
        @Index(name = "IDX_BOOKSTOREORDERLINE_PRODUCT", columnList = "PRODUCT_ID")
})
@Entity(name = "bookstore_OrderLine")
public class OrderLine extends StandardTenantEntity {

    @NotNull
    @JoinColumn(name = "ORDER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;

    @InstanceName
    @NotNull
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
