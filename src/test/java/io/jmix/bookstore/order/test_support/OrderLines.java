package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.entity.test_support.MoneyData;
import io.jmix.bookstore.entity.test_support.MoneyMapper;
import io.jmix.bookstore.order.*;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderLines
        implements TestDataProvisioning<OrderLineData, OrderLineData.OrderLineDataBuilder, OrderLine> {

    @Autowired
    DataManager dataManager;
    @Autowired
    private OrderLineMapper orderLineMapper;
    @Autowired
    private MoneyMapper moneyMapper;
    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    Orders orders;
    @Autowired
    Products products;

    private static final BigDecimal DEFAULT_UNIT_PRICE_AMOUNT = BigDecimal.TEN;
    private static final Currency DEFAULT_CURRENCY = Currency.USD;
    private static final BigDecimal DEFAULT_DISCOUNT_AMOUNT = BigDecimal.ZERO;
    private static final Integer DEFAULT_QUANTITY = 1;
    @Override
    public OrderLineData.OrderLineDataBuilder defaultData() {
        return OrderLineData.builder()
                .order(orders.createDefault())
                .unitPrice(money(DEFAULT_UNIT_PRICE_AMOUNT, DEFAULT_CURRENCY))
                .discount(money(DEFAULT_DISCOUNT_AMOUNT, DEFAULT_CURRENCY))
                .quantity(DEFAULT_QUANTITY)
                .product(products.createDefault());
    }

    @Override
    public OrderLine save(OrderLineData orderLineData) {
        return orderLineRepository.save(orderLineData);
    }

    @Override
    public OrderLine create(OrderLineData orderLineData) {
        return orderLineMapper.toEntity(orderLineData);
    }

    @Override
    public OrderLine createDefault() {
        return create(defaultData().build());
    }

    @Override
    public OrderLine saveDefault() {
        return save(defaultData().build());
    }

    public Money money(BigDecimal amount, Currency currency) {
        return moneyMapper.toEntity(MoneyData.builder()
                .amount(amount)
                .currency(currency)
                .build());
    }
}
