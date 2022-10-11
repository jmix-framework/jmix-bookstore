package io.jmix.bookstore.product.screen;

import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.screen.productcategory.ProductCategoryBrowse;
import io.jmix.bookstore.product.screen.productcategory.ProductCategoryEdit;
import io.jmix.bookstore.product.test_support.ProductCategories;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.bookstore.test_support.ui.ScreenInteractions;
import io.jmix.bookstore.test_support.ui.TableInteractions;
import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.jmix.bookstore.order.Assertions.assertThat;

class ProductCategoryBrowseTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;

    private ProductCategory productCategory;
    @Autowired
    private ProductCategories productCategories;


    @BeforeEach
    void setUp() {

        databaseCleanup.removeAllEntities();
        productCategory = productCategories.saveDefault();
    }

    @Test
    void given_oneProductCategoryExists_when_openProductCategoryBrowse_then_tableContainsTheProductCategory(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductCategoryBrowse productCategoryBrowse = screenInteractions.open(ProductCategoryBrowse.class);
        TableInteractions<ProductCategory> productCategoryTable = productCategoryTable(productCategoryBrowse);

        // expect:
        assertThat(productCategoryTable.firstItem())
                .isEqualTo(productCategory);
    }


    @Test
    void given_oneProductCategoryExists_when_editProductCategory_then_editProductCategoryEditorIsShown(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductCategoryBrowse productCategoryBrowse = screenInteractions.open(ProductCategoryBrowse.class);
        TableInteractions<ProductCategory> productCategoryTable = productCategoryTable(productCategoryBrowse);

        // and:
        ProductCategory firstProductCategory = productCategoryTable.firstItem();

        // and:
        productCategoryTable.edit(firstProductCategory);

        // then:
        ProductCategoryEdit productCategoryEdit = screenInteractions.findOpenScreen(ProductCategoryEdit.class);

        assertThat(productCategoryEdit.getEditedEntity())
                .isEqualTo(firstProductCategory);
    }

    @NotNull
    private TableInteractions<ProductCategory> productCategoryTable(ProductCategoryBrowse productCategoryBrowse) {
        return TableInteractions.of(productCategoryBrowse, ProductCategory.class, "productCategoriesTable");
    }
}
