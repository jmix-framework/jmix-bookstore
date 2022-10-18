package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.entity.test_support.MoneyData;
import io.jmix.bookstore.entity.test_support.MoneyMapper;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Products
        implements TestDataProvisioning<ProductData, ProductData.ProductDataBuilder, Product> {

    @Autowired
    DataManager dataManager;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private MoneyMapper moneyMapper;

    public static final String DEFAULT_NAME = "product_name";
    private static final BigDecimal DEFAULT_UNIT_PRICE_AMOUNT = BigDecimal.TEN;
    private static final Currency DEFAULT_CURRENCY = Currency.USD;

    @Override
    public ProductData.ProductDataBuilder defaultData() {
        return ProductData.builder()
                .name(DEFAULT_NAME)
                .unitPrice(money(DEFAULT_UNIT_PRICE_AMOUNT, DEFAULT_CURRENCY))
                .category(null);
    }

    @Override
    public Product save(ProductData productData)  {
        return productRepository.save(productData);
    }

    @Override
    public Product create(ProductData productData) {
        return productMapper.toEntity(productData);
    }

    @Override
    public Product createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Product saveDefault() {
        return save(defaultData().build());
    }

    public Money money(BigDecimal amount, Currency currency) {
        return moneyMapper.toEntity(MoneyData.builder()
                .amount(amount)
                .currency(currency)
                .build());
    }
}
