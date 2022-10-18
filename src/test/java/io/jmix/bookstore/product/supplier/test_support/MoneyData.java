package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.product.supplier.Currency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyData {
    BigDecimal amount;
    Currency currency;
}
