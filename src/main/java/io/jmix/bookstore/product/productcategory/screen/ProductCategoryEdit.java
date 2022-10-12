package io.jmix.bookstore.product.productcategory.screen;

import io.jmix.bookstore.product.ProductCategory;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("bookstore_ProductCategory.edit")
@UiDescriptor("product-category-edit.xml")
@EditedEntityContainer("productCategoryDc")
public class ProductCategoryEdit extends StandardEditor<ProductCategory> {
}
