package io.jmix.bookstore.product.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.Product;

@UiController("bookstore_Product.lookup")
@UiDescriptor("product-lookup.xml")
@LookupComponent("productsTable")
public class ProductLookup extends StandardLookup<Product> {
}
