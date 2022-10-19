package io.jmix.bookstore.product;


import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.bookstore.product.test_support.ProductCategories;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class ProductStorageTest {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private Products products;
    @Autowired
    private ProductCategories productCategories;

    @Test
    void given_validProduct_when_save_then_productIsSaved() {

        // when
        Product product = products.save(
                products.defaultData()
                        .name("Foo Product")
                        .build()
        );

        // then
        assertThat(product.getId())
                .isNotNull();
    }

    @Test
    void given_validProductWithCategory_when_save_then_productAndCategoryAssociationAreSaved() {

        // given
        ProductCategory productCategory = productCategories.saveDefault();

        // when
        Product product = products.save(
                products.defaultData()
                        .category(productCategory)
                        .build()
        );

        // then
        assertThat(loadProductWithCategory(product))
                .isPresent()
                .get()
                .extracting("category")
                .isEqualTo(productCategory);
    }

    @NotNull
    private Optional<Product> loadProductWithCategory(Product product) {
        return dataManager.load(Id.of(product))
                .fetchPlan(productFp -> {
                    productFp.addFetchPlan(FetchPlan.BASE);
                    productFp.add("category", categoryFp -> categoryFp.addFetchPlan(FetchPlan.BASE));
                })
                .optional();
    }
}
