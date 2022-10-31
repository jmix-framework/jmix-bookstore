package io.jmix.bookstore.product.screen;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.bookstore.product.supplier.SupplierOrderRequestStatus;
import io.jmix.bookstore.product.supplier.screen.supplierorderrequest.SupplierOrderRequestEdit;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.NotificationFacet;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.Product;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
public class ProductBrowse extends StandardLookup<Product> {

    @Autowired
    private DataGrid<Product> productsTable;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private NotificationFacet supplierOrderRequestCreatedConfirmation;


    @Subscribe("productsTable.fillUpInventory")
    public void onProductsTableFillUpInventory(Action.ActionPerformedEvent event) {

        SupplierOrderRequestEdit supplierOrderRequestEdit = screenBuilders.editor(SupplierOrderRequest.class, this)
                .withScreenClass(SupplierOrderRequestEdit.class)
                .withInitializer(supplierOrderRequest -> {
                    supplierOrderRequest.setRequestedAmount(100);
                    supplierOrderRequest.setStatus(SupplierOrderRequestStatus.NEW);
                    supplierOrderRequest.setProduct(productsTable.getSingleSelected());

                    User user = (User) currentAuthentication.getUser();
                    supplierOrderRequest.setRequestedBy(user);
                })
                .build();

        supplierOrderRequestEdit.addAfterCloseListener(afterCloseEvent -> {
            if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                supplierOrderRequestCreatedConfirmation.show();
            }
        });

        supplierOrderRequestEdit
                .show();

    }

    @Install(to = "productsTable.fillUpInventory", subject = "enabledRule")
    private boolean productsTableFillUpInventoryEnabledRule() {
        return Boolean.TRUE.equals(productsTable.getSingleSelected().getActive());
    }
}
