package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.product.ProductCategory;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.Book;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("bookstore_ProductCategoryDataProvider")
public class ProductCategoryDataProvider implements TestDataProvider<ProductCategory, ProductCategoryDataProvider.Dependencies> {

    protected final DataManager dataManager;

    public record Dependencies(){}
    public ProductCategoryDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<ProductCategory> create(int amount, Dependencies dependencies) {
        return commit(createProductCategories(amount));
    }

    private List<ProductCategory> createProductCategories(int amount) {
        Faker faker = new Faker();

        return Stream.generate(faker::book).limit(amount)
                .map(this::toProductCategory)
                .collect(Collectors.groupingBy(ProductCategory::getName))
                .values().stream()
                .map(productCategories -> productCategories.get(0))
                .collect(Collectors.toList());
    }

    private ProductCategory toProductCategory(Book book) {
        ProductCategory productCategory = dataManager.create(ProductCategory.class);
        productCategory.setName(book.genre());
        return productCategory;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}
