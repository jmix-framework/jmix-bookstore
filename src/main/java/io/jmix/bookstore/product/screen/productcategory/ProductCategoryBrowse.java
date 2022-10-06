package io.jmix.bookstore.product.screen.productcategory;

import io.jmix.bookstore.product.ProductCategory;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("bookstore_ProductCategory.browse")
@UiDescriptor("product-category-browse.xml")
@LookupComponent("productCategoriesTable")
public class ProductCategoryBrowse extends StandardLookup<ProductCategory> {
}
