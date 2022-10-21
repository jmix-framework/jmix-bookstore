package io.jmix.bookstore.product.supplier;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "bookstore_PerformSupplierOrderService")
public class PerformSupplierOrderService {


    private final DataManager dataManager;

    public PerformSupplierOrderService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void createDraftSupplierOrders() {
        List<SupplierOrderRequest> supplierOrderRequests = dataManager.load(SupplierOrderRequest.class)
                .all()
                .fetchPlan(this::supplierOrderRequestsFetchPlan)
                .list();
    }

    private void supplierOrderRequestsFetchPlan(FetchPlanBuilder supplierOrderRequestFp) {
        supplierOrderRequestFp.addFetchPlan(FetchPlan.BASE);
        supplierOrderRequestFp.add("product", this::productFetchPlanBuilder);
    }

    private void productFetchPlanBuilder(FetchPlanBuilder productFp) {
        productFp.addFetchPlan(FetchPlan.BASE);
        productFp.add("supplier", FetchPlan.BASE);
    }

}
