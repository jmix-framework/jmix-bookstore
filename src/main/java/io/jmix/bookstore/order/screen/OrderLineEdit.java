package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.order.OrderLine;
import io.jmix.bookstore.product.Product;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.*;

import java.math.BigDecimal;

@UiController("bookstore_OrderLine.edit")
@UiDescriptor("order-line-edit.xml")
@EditedEntityContainer("orderLineDc")
public class OrderLineEdit extends StandardEditor<OrderLine> {
    @Subscribe
    public void onInitEntity(InitEntityEvent<OrderLine> event) {
        event.getEntity().setQuantity(1);
    }

    @Subscribe("productField")
    public void onProductFieldValueChange(HasValue.ValueChangeEvent<Product> event) {
        Money orderLineUnitPrice = getEditedEntity().getUnitPrice();
        Money orderLineDiscount = getEditedEntity().getDiscount();

        if (event.getValue() != null) {

            Money productUnitPrice = getEditedEntity().getProduct().getUnitPrice();
            Currency currency = productUnitPrice.getCurrency();

            orderLineUnitPrice.setCurrency(currency);
            orderLineUnitPrice.setAmount(productUnitPrice.getAmount());

            orderLineDiscount.setAmount(BigDecimal.ZERO);
            orderLineDiscount.setCurrency(currency);
        }
        else {
            orderLineUnitPrice.setAmount(null);
            orderLineUnitPrice.setCurrency(null);

            orderLineDiscount.setAmount(null);
            orderLineDiscount.setCurrency(null);
        }
    }





}
