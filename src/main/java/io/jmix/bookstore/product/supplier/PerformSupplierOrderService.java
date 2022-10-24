package io.jmix.bookstore.product.supplier;

import io.jmix.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "bookstore_PerformSupplierOrderService")
public class PerformSupplierOrderService {
    private static final Logger log = LoggerFactory.getLogger(PerformSupplierOrderService.class);


    private final DataManager dataManager;
    private final TimeSource timeSource;

    public PerformSupplierOrderService(DataManager dataManager, TimeSource timeSource) {
        this.dataManager = dataManager;
        this.timeSource = timeSource;
    }

    public void notifyRequestersAboutInvalidRequest(SupplierOrder supplierOrder) {

        log.info("Notifying requesters about invalid requests for supplier Order: {}", supplierOrder);
    }


    public void placeSupplierOrder(SupplierOrder supplierOrder) {
        log.info("Placing Supplier Order: {}", supplierOrder);
    }


    public void createDraftSupplierOrders() {
        List<SupplierOrderRequest> supplierOrderRequests = dataManager.load(SupplierOrderRequest.class)
                .all()
                .fetchPlan(this::supplierOrderRequestsFetchPlan)
                .list();

        Map<Supplier, List<SupplierOrderRequest>> requestsBySupplier = supplierOrderRequests.stream()
                .collect(Collectors.groupingBy(supplierOrderRequest -> supplierOrderRequest.getProduct().getSupplier()));


        SaveContext saveContext = new SaveContext();
        requestsBySupplier.entrySet().stream()
                .map(supplierListEntry -> createSupplierOrder(supplierListEntry.getKey(), supplierListEntry.getValue()))
                .forEach(saveContext::saving);

        dataManager.save(saveContext);
    }

    private SupplierOrder createSupplierOrder(Supplier supplier, List<SupplierOrderRequest> requests) {

        SupplierOrder supplierOrder = dataManager.create(SupplierOrder.class);
        supplierOrder.setSupplier(supplier);
        supplierOrder.setStatus(SupplierOrderStatus.DRAFT);
        supplierOrder.setOrderDate(timeSource.now().toLocalDate().plusDays(7));
        supplierOrder.setOrderLines(createSupplierOrderLines(supplierOrder, requests));
        return supplierOrder;
    }

    private List<SupplierOrderLine> createSupplierOrderLines(SupplierOrder supplierOrder, List<SupplierOrderRequest> requests) {
        return requests.stream()
                .map(supplierOrderRequest -> createSupplierOrderLine(supplierOrder, supplierOrderRequest))
                .collect(Collectors.toList());
    }

    private SupplierOrderLine createSupplierOrderLine(SupplierOrder supplierOrder, SupplierOrderRequest supplierOrderRequest) {
        SupplierOrderLine supplierOrderLine = dataManager.create(SupplierOrderLine.class);
        supplierOrderLine.setProduct(supplierOrderRequest.getProduct());
        supplierOrderLine.setQuantity(supplierOrderRequest.getRequestedAmount());
        supplierOrderLine.setSupplierOrder(supplierOrder);
        supplierOrderLine.setRequest(supplierOrderRequest);
        return supplierOrderLine;
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
