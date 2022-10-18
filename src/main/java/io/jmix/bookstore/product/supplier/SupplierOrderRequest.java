package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.product.Product;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_SUPPLIER_ORDER_REQUEST", indexes = {
        @Index(name = "IDX_BOOKSTORE_SUPPLIER_ORDER_REQUEST_PRODUCT", columnList = "PRODUCT_ID")
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
