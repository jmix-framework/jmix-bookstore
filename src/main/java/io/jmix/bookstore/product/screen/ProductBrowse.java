package io.jmix.bookstore.product.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.Product;

@UiController("bookstore_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
public class ProductBrowse extends StandardLookup<Product> {
}
