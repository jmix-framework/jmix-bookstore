package io.jmix.bookstore;

import io.jmix.bookstore.product.supplier.Currency;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConstructorBinding
public class BookstoreProperties {

    @NotNull
    Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
