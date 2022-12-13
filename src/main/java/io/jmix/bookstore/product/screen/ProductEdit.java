package io.jmix.bookstore.product.screen;

import io.jmix.bookstore.product.Product;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("bookstore_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
@Route(value = "products/edit", parentPrefix = "products")
public class ProductEdit extends StandardEditor<Product> {
}
