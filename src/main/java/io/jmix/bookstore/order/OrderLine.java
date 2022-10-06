package io.jmix.bookstore.order;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.bookstore.order.validation.ValidRentalPeriod;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.validation.group.UiCrossFieldChecks;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDateTime;

@JmixEntity
@Table(name = "BOOKSTORE_ORDER_LINE", indexes = {
        @Index(name = "IDX_ORDERLINE_ORDER_ID", columnList = "ORDER_ID")
})
@Entity(name = "bookstore_OrderLine")
@ValidRentalPeriod(groups = {Default.class, UiCrossFieldChecks.class})
public class OrderLine extends StandardTenantEntity {

    @NotNull
    @JoinColumn(name = "ORDER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;

    @FutureOrPresent
    @NotNull
    @Column(name = "STARTS_AT", nullable = false)
    private LocalDateTime startsAt;

    @FutureOrPresent
    @NotNull
    @Column(name = "ENDS_AT", nullable = false)
    private LocalDateTime endsAt;

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
