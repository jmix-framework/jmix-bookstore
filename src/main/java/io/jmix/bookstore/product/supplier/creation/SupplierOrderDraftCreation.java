package io.jmix.bookstore.product.supplier.creation;

import io.jmix.bookstore.product.supplier.*;
import io.jmix.core.*;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.core.querycondition.PropertyCondition;
import org.flowable.engine.RuntimeService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("bookstore_SupplierOrderDraftCreation")
public class SupplierOrderDraftCreation {

    private final DataManager dataManager;
    private final RuntimeService runtimeService;
    private final TimeSource timeSource;
    private final DatatypeFormatter datatypeFormatter;

    public SupplierOrderDraftCreation(DataManager dataManager, RuntimeService runtimeService, TimeSource timeSource, DatatypeFormatter datatypeFormatter) {
        this.dataManager = dataManager;
        this.runtimeService = runtimeService;
        this.timeSource = timeSource;
        this.datatypeFormatter = datatypeFormatter;
    }


    public int createDraftSupplierOrders() {
        List<SupplierOrderRequest> supplierOrderRequests = dataManager.load(SupplierOrderRequest.class)
                .condition(PropertyCondition.equal("status", SupplierOrderRequestStatus.NEW))
                .fetchPlan(this::supplierOrderRequestsFetchPlan)
                .list();


        Map<Supplier, List<SupplierOrderRequest>> requestsBySupplier = supplierOrderRequests.stream()
                .collect(Collectors.groupingBy(supplierOrderRequest -> supplierOrderRequest.getProduct().getSupplier()));


        SaveContext saveContext = new SaveContext();
        List<SupplierOrder> supplierOrders = requestsBySupplier.entrySet().stream()
                .map(supplierListEntry -> createSupplierOrder(supplierListEntry.getKey(), supplierListEntry.getValue())).toList();

        supplierOrderRequests.forEach(supplierOrderRequest -> supplierOrderRequest.setStatus(SupplierOrderRequestStatus.ORDER_LINE_CREATED));
        supplierOrderRequests.forEach(saveContext::saving);
        supplierOrders
                .forEach(saveContext::saving);

        dataManager.save(saveContext);

        supplierOrders.forEach(this::startProcess);
        return supplierOrders.size();
    }

    private void startProcess(SupplierOrder supplierOrder) {
        Map<String, Object> params = new HashMap<>();
        params.put("supplierOrder", supplierOrder);

        runtimeService.startProcessInstanceByKeyAndTenantId(
                "perform-supplier-order",
                businessKey(supplierOrder),
                params,
                supplierOrder.getTenant()
        );
    }

    private String businessKey(SupplierOrder supplierOrder) {
        return "%s (%s)".formatted(supplierOrder.getSupplier().getName(), datatypeFormatter.formatLocalDate(supplierOrder.getOrderDate()));
    }

    private SupplierOrder createSupplierOrder(Supplier supplier, List<SupplierOrderRequest> requests) {

        SupplierOrder supplierOrder = dataManager.create(SupplierOrder.class);
        supplierOrder.setTenant(supplier.getTenant());
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
        supplierOrderLine.setTenant(supplierOrderRequest.getTenant());
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
