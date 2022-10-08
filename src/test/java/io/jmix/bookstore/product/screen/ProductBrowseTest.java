package io.jmix.bookstore.product.screen;

import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.bookstore.test_support.ui.ScreenInteractions;
import io.jmix.bookstore.test_support.ui.TableInteractions;
import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ProductBrowseTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    private Product product;
    @Autowired
    private Products products;


    @BeforeEach
    void setUp() {
        product = products.saveDefault();
    }

    @Test
    void given_oneProductExists_when_openProductBrowse_then_tableContainsTheProduct(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductBrowse productBrowse = screenInteractions.open(ProductBrowse.class);
        TableInteractions<Product> productTable = productTable(productBrowse);

        // expect:
        assertThat(productTable.firstItem())
                .isEqualTo(product);
    }


    @Test
    void given_oneProductExists_when_editProduct_then_editProductEditorIsShown(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductBrowse productBrowse = screenInteractions.open(ProductBrowse.class);
        TableInteractions<Product> productTable = productTable(productBrowse);

        // and:
        Product firstProduct = productTable.firstItem();

        // and:
        productTable.edit(firstProduct);

        // then:
        ProductEdit productEdit = screenInteractions.findOpenScreen(ProductEdit.class);

        assertThat(productEdit.getEditedEntity())
                .isEqualTo(firstProduct);
    }

    @NotNull
    private TableInteractions<Product> productTable(ProductBrowse productBrowse) {
        return TableInteractions.of(productBrowse, Product.class, "productsTable");
    }
}
