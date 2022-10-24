package io.jmix.bookstore.product.supplier.screen.supplierorder;

import io.jmix.bookstore.product.supplier.PerformSupplierOrderService;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.NotificationFacet;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UiController("bookstore_SupplierOrder.browse")
@UiDescriptor("supplier-order-browse.xml")
@LookupComponent("supplierOrdersTable")
public class SupplierOrderBrowse extends StandardLookup<SupplierOrder> {
    @Autowired
    private PerformSupplierOrderService performSupplierOrderService;
    @Autowired
    private NotificationFacet supplierOrdersFromRequestsCreatedConfirmation;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private DataGrid<SupplierOrder> supplierOrdersTable;

    @Subscribe("supplierOrdersTable.createSupplierOrdersFromRequests")
    public void onSupplierOrdersTableCreateSupplierOrdersFromRequests(Action.ActionPerformedEvent event) {
        performSupplierOrderService.createDraftSupplierOrders();

        getScreenData().loadAll();

        supplierOrdersFromRequestsCreatedConfirmation.show();
    }

    @Subscribe("supplierOrdersTable.performSupplierOrderProcess")
    public void onSupplierOrdersTablePerformSupplierOrderProcess(Action.ActionPerformedEvent event) {
        Set<SupplierOrder> supplierOrders = supplierOrdersTable.getSelected();

        supplierOrders.forEach(this::performSupplierOrder);

    }

    private void performSupplierOrder(SupplierOrder supplierOrder) {
        Map<String, Object> params = new HashMap<>();
        params.put("supplierOrder", supplierOrder);
        runtimeService.startProcessInstanceByKey(
                "perform-supplier-order",
                params);
    }
}
