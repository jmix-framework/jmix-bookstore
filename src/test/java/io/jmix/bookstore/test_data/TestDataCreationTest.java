package io.jmix.bookstore.test_data;

import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.product.test_support.ProductCategories;
import io.jmix.bookstore.product.test_support.Suppliers;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class TestDataCreationTest {

    @Autowired
    TestDataCreation testDataCreation;

    @Autowired
    TimeSource timeSource;
    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    ProductCategories productCategories;
    @Autowired
    Suppliers suppliers;


    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities();
    }

    @Nested
    class CreateProducts {

        @Test
        void when_generateProducts_thenProductsAreStored() {

            // given:
            assertThat(dbProducts())
                    .isEmpty();

            // and:
            ProductCategory productCategory1 = productCategories.saveDefault();
            ProductCategory productCategory2 = productCategories.saveDefault();

            // and:
            Supplier supplier1 = suppliers.saveDefault();
            Supplier supplier2 = suppliers.saveDefault();


            // when:
            List<Product> products = testDataCreation.generateProducts(1_000, List.of(
                    productCategory1,
                    productCategory2
            ), List.of(supplier1, supplier2));

            // then:
            assertThat(products.size())
                    .isLessThanOrEqualTo(1_000);
            // and:
            assertThat(dbProducts().size())
                    .isLessThanOrEqualTo(1_000);
        }


        @Test
        void when_generateProductCategories_thenProductCategoriesAreStored() {

            // given:
            assertThat(dbProductCategories())
                    .isEmpty();


            // when:
            List<ProductCategory> categories = testDataCreation.generateProductCategories(100);

            // then:
            assertThat(categories.size())
                    .isLessThanOrEqualTo(100);

            // and:
            assertThat(dbProductCategories().size())
                    .isLessThanOrEqualTo(100);
        }
    }

    @NotNull
    private List<Product> dbProducts() {
        return allEntitiesOf(Product.class);
    }
    @NotNull
    private <T> List<T> allEntitiesOf(Class<T> entityClass) {
        return dataManager.load(entityClass).all().list();
    }

    private List<ProductCategory> dbProductCategories() {
        return allEntitiesOf(ProductCategory.class);
    }

}
