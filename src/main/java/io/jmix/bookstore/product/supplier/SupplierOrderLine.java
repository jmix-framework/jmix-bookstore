package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.entity.StandardEntity;
import io.jmix.bookstore.product.Product;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_SUPPLIER_ORDER_LINE", indexes = {
        @Index(name = "IDX_BOOKSTORE_SUPPLIER_ORDER_LINE_PRODUCT", columnList = "PRODUCT_ID"),
        @Index(name = "IDX_BOOKSTORE_SUPPLIER_ORDER_LINE_SUPPLIER_ORDER", columnList = "SUPPLIER_ORDER_ID")
})
@Entity(name = "bookstore_SupplierOrderLine")
public class SupplierOrderLine extends StandardEntity {
    @JoinColumn(name = "SUPPLIER_ORDER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SupplierOrder supplierOrder;

    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Column(name = "QUANTITY", nullable = false)
    @NotNull
    private Integer quantity;

    public SupplierOrder getSupplierOrder() {
        return supplierOrder;
    }

    public void setSupplierOrder(SupplierOrder supplierOrder) {
        this.supplierOrder = supplierOrder;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
