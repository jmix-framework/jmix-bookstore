package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.entity.StandardEntity;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.Product;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_SUPPLIER_ORDER_REQUEST", indexes = {
        @Index(name = "IDX_BOOKSTORE_SUPPLIER_ORDER_REQUEST_PRODUCT", columnList = "PRODUCT_ID"),
        @Index(name = "IDX_BOOKSTORE_SUPPLIER_ORDER_REQUEST_REQUESTED_BY", columnList = "REQUESTED_BY_ID")
})
@Entity(name = "bookstore_SupplierOrderRequest")
public class SupplierOrderRequest extends StandardEntity {
    @InstanceName
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Column(name = "REQUESTED_AMOUNT", nullable = false)
    @NotNull
    private Integer requestedAmount;

    @Column(name = "COMMENT_")
    @Lob
    private String comment;

    @NotNull
    @JoinColumn(name = "REQUESTED_BY_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User requestedBy;

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
