package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.Product;
import io.jmix.core.*;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.entity.ContentType;
import org.flowable.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "bookstore_PerformSupplierOrderService")
public class PerformSupplierOrderService {
    private static final Logger log = LoggerFactory.getLogger(PerformSupplierOrderService.class);


    private final DataManager dataManager;
    private final TimeSource timeSource;
    protected final NotificationManager notificationManager;
    private final DatatypeFormatter datatypeFormatter;
    private final RuntimeService runtimeService;

    public PerformSupplierOrderService(DataManager dataManager, TimeSource timeSource, NotificationManager notificationManager, DatatypeFormatter datatypeFormatter, RuntimeService runtimeService) {
        this.dataManager = dataManager;
        this.timeSource = timeSource;
        this.notificationManager = notificationManager;
        this.datatypeFormatter = datatypeFormatter;
        this.runtimeService = runtimeService;
    }

    /**
     * Notifies the requester about a declined request through the BPM process.
     * @param supplierOrder the supplier order that was declined
     */
    @SuppressWarnings("UnusedMethod")
    public void notifyRequestersAboutInvalidRequest(SupplierOrder supplierOrder) {

        log.info("Notifying requesters about invalid requests for supplier Order: {}", supplierOrder);

        supplierOrder.getOrderLines().stream().map(this::declinedRequestNotification)
                .forEach(NotificationManager.SendNotification::send);
    }

    private NotificationManager.NotificationRequestBuilder declinedRequestNotification(SupplierOrderLine supplierOrderLine) {
        SupplierOrderRequest request = supplierOrderLine.getRequest();

        Product requestedProduct = request.getProduct();
        String productName = requestedProduct.getName();

        String subject = "Fill-Up request for %s declined".formatted(productName);
        String body = "Your fill-up request from %s for %s has been declined.".formatted(
                datatypeFormatter.formatDate(request.getCreatedDate()),
                productName
        );
        return requesterNotification(supplierOrderLine, subject, body);
    }

    private NotificationManager.NotificationRequestBuilder placedOrderNotification(SupplierOrderLine supplierOrderLine) {
        SupplierOrderRequest request = supplierOrderLine.getRequest();

        Product requestedProduct = request.getProduct();
        String productName = requestedProduct.getName();

        String subject = "Order with Fill-Up request placed".formatted(productName);
        String body = "Your fill-up request from %s for %s has been approved and an order has been placed.".formatted(
                datatypeFormatter.formatDate(request.getCreatedDate()),
                productName
        );
        return requesterNotification(supplierOrderLine, subject, body);
    }
    private NotificationManager.NotificationRequestBuilder requesterNotification(SupplierOrderLine supplierOrderLine, String subject, String body) {
        User requestedBy = supplierOrderLine.getRequest().getRequestedBy();
        return notificationManager.createNotification()
                .withSubject(subject)
                .withRecipientUsernames(requestedBy.getUsername())
                .toChannelsByNames("in-app")
                .withContentType(ContentType.PLAIN)
                .withBody(body);
    }


    /**
     * places a supplier order through the BPM process
     * @param supplierOrder the supplier order to place
     */
    public void placeSupplierOrder(SupplierOrder supplierOrder) {
        log.info("Placing Supplier Order: {}", supplierOrder);

        supplierOrder.getOrderLines().stream().map(this::placedOrderNotification)
                .forEach(NotificationManager.SendNotification::send);
    }



    public void createDraftSupplierOrders() {
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
    }

    private void startProcess(SupplierOrder supplierOrder) {
        Map<String, Object> params = new HashMap<>();
        params.put("supplierOrder", supplierOrder);
        runtimeService.startProcessInstanceByKey(
                "perform-supplier-order",
                businessKey(supplierOrder),
                params);
    }

    private String businessKey(SupplierOrder supplierOrder) {
        return "%s (%s)".formatted(supplierOrder.getSupplier().getName(), datatypeFormatter.formatLocalDate(supplierOrder.getOrderDate()));
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
