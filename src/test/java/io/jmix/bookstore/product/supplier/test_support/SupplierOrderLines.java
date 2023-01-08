package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.entity.test_support.MoneyData;
import io.jmix.bookstore.entity.test_support.MoneyMapper;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SupplierOrderLines
        implements TestDataProvisioning<SupplierOrderLineData, SupplierOrderLineData.SupplierOrderLineDataBuilder, SupplierOrderLine> {

    @Autowired
    DataManager dataManager;
    @Autowired
    private SupplierOrderLineMapper supplierOrderLineMapper;
    @Autowired
    private MoneyMapper moneyMapper;
    @Autowired
    SupplierOrderLineRepository supplierOrderLineRepository;

    @Autowired
    SupplierOrders supplierOrders;
    @Autowired
    Products products;

    private static final BigDecimal DEFAULT_UNIT_PRICE_AMOUNT = BigDecimal.TEN;
    private static final Currency DEFAULT_CURRENCY = Currency.USD;
    private static final BigDecimal DEFAULT_DISCOUNT_AMOUNT = BigDecimal.ZERO;
    private static final Integer DEFAULT_QUANTITY = 1;
    @Override
    public SupplierOrderLineData.SupplierOrderLineDataBuilder defaultData() {
        return SupplierOrderLineData.builder()
                .supplierOrder(supplierOrders.createDefault())
                .unitPrice(money(DEFAULT_UNIT_PRICE_AMOUNT, DEFAULT_CURRENCY))
                .discount(money(DEFAULT_DISCOUNT_AMOUNT, DEFAULT_CURRENCY))
                .quantity(DEFAULT_QUANTITY)
                .product(products.createDefault());
    }

    @Override
    public SupplierOrderLine save(SupplierOrderLineData supplierOrderLineData) {
        return supplierOrderLineRepository.save(supplierOrderLineData);
    }

    @Override
    public SupplierOrderLine create(SupplierOrderLineData supplierOrderLineData) {
        return supplierOrderLineMapper.toEntity(supplierOrderLineData);
    }

    @Override
    public SupplierOrderLine createDefault() {
        return create(defaultData().build());
    }

    @Override
    public SupplierOrderLine saveDefault() {
        return save(defaultData().build());
    }

    public Money money(BigDecimal amount, Currency currency) {
        return moneyMapper.toEntity(MoneyData.builder()
                .amount(amount)
                .currency(currency)
                .build());
    }
}
