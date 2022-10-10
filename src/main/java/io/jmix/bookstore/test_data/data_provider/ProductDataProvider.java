package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.Book;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomEnum;
import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_ProductDataProvider")
public class ProductDataProvider implements TestDataProvider<Product, ProductDataProvider.Dependencies> {

    public record Dependencies(List<ProductCategory> productCategories){}

    protected final DataManager dataManager;

    public ProductDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Product> create(int amount, Dependencies dependencies) {
        return commit(createProducts(amount, dependencies.productCategories()));
    }

    private List<Product> createProducts(int amount, List<ProductCategory> productCategories) {
        Faker faker = new Faker();

        return Stream.generate(faker::book).limit(amount)
                .map(book -> toProduct(book, productCategories))
                .collect(Collectors.groupingBy(Product::getName))
                .values().stream()
                .map(products -> products.get(0))
                .collect(Collectors.toList());
    }

    private  Product toProduct(Book book, List<ProductCategory> productCategories) {
        Product product = dataManager.create(Product.class);
        product.setActive(true);
        product.setName(book.title());
        product.setCategory(randomOfList(productCategories));
        product.setUnitPrice(randomPrice());
        return product;
    }

    private Money randomPrice() {
        Faker faker = new Faker();
        Money money = dataManager.create(Money.class);
        money.setAmount(BigDecimal.valueOf(faker.number().numberBetween(15L,80L)));
        money.setCurrency(randomEnum(Currency.values()));
        return money;
    }
    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}
