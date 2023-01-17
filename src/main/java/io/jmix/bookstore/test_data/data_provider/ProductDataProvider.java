package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.Book;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.*;

@Component("bookstore_ProductDataProvider")
public class ProductDataProvider implements TestDataProvider<Product, ProductDataProvider.DataContext> {

    public record DataContext(int amount, List<ProductCategory> productCategories, List<Supplier> suppliers){}

    protected final DataManager dataManager;

    public ProductDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Product> create(DataContext dataContext) {
        return commit(createProducts(dataContext.amount(), dataContext.productCategories(), dataContext.suppliers()));
    }

    private List<Product> createProducts(int amount, List<ProductCategory> productCategories, List<Supplier> suppliers) {
        Faker faker = new Faker();

        return Stream.generate(faker::book).limit(amount)
                .map(book -> toProduct(book, productCategories, suppliers))
                .collect(Collectors.groupingBy(Product::getName))
                .values().stream()
                .map(products -> products.get(0))
                .collect(Collectors.toList());
    }

    private  Product toProduct(Book book, List<ProductCategory> productCategories, List<Supplier> suppliers) {
        Product product = dataManager.create(Product.class);
        product.setActive(randomOfList(true, true, false));
        product.setName(book.title());
        product.setCategory(randomOfList(productCategories));
        product.setSupplier(randomOfList(suppliers));
        product.setUnitPrice(randomPrice());
        product.setUnitsOnOrder(0);

        int unitsInStock = randomPositiveNumber(200);
        product.setUnitsInStock(unitsInStock);

        if (unitsInStock < 50) {
            product.setUnitsOnOrder(randomOfList(
                    0,
                    randomPositiveNumber(100, 400)
            ));
        }

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
        saveContext.setDiscardSaved(true);
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}
