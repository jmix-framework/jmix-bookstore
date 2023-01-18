package io.jmix.bookstore.product.supplier;

import com.haulmont.yarg.reporting.ReportOutputDocument;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.Product;
import io.jmix.bpm.multitenancy.BpmTenantProvider;
import io.jmix.core.*;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.multitenancy.core.TenantProvider;
import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.entity.ContentType;
import io.jmix.reports.runner.ReportRunner;
import org.flowable.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
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
    private final ReportRunner reportRunner;
    private final FileStorage fileStorage;
    private final TenantProvider tenantProvider;

    public PerformSupplierOrderService(DataManager dataManager, TimeSource timeSource, NotificationManager notificationManager, DatatypeFormatter datatypeFormatter, RuntimeService runtimeService, ReportRunner reportRunner, FileStorage fileStorage, TenantProvider tenantProvider) {
        this.dataManager = dataManager;
        this.timeSource = timeSource;
        this.notificationManager = notificationManager;
        this.datatypeFormatter = datatypeFormatter;
        this.runtimeService = runtimeService;
        this.reportRunner = reportRunner;
        this.fileStorage = fileStorage;
        this.tenantProvider = tenantProvider;
    }

    /**
     * Notifies the requester about a declined order through the BPM process.
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

        String subject = "Fill-Up order for %s declined".formatted(productName);
        String body = "Your fill-up order from %s for %s has been declined.".formatted(
                datatypeFormatter.formatDate(request.getCreatedDate()),
                productName
        );
        return requesterNotification(supplierOrderLine, subject, body);
    }

    private NotificationManager.NotificationRequestBuilder placedOrderNotification(SupplierOrderLine supplierOrderLine) {
        SupplierOrderRequest request = supplierOrderLine.getRequest();

        Product requestedProduct = request.getProduct();
        String productName = requestedProduct.getName();

        String subject = "Order with Fill-Up order placed".formatted(productName);
        String body = "Your fill-up order from %s for '%s' has been approved and an order has been placed.".formatted(
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
    @SuppressWarnings("UnusedMethod")
    public FileRef placeSupplierOrder(SupplierOrder supplierOrder, User reviewedBy) {
        log.info("Placing Supplier Order: {}", supplierOrder);

        SupplierOrder reloadedSupplierOrder = dataManager.load(Id.of(supplierOrder)).one();

        reloadedSupplierOrder.setStatus(SupplierOrderStatus.ORDERED);

        SupplierOrder supplierOrderWithUpdatedStatus = dataManager.save(reloadedSupplierOrder);

        supplierOrder.getOrderLines().stream().map(this::placedOrderNotification)
                .forEach(NotificationManager.SendNotification::send);

        ReportOutputDocument document = reportRunner.byReportCode("supplier-order-form")
                .addParam("entity", reloadedSupplierOrder)
                .addParam("reviewedBy", reviewedBy)
                .run();

        ByteArrayInputStream documentBytes = new ByteArrayInputStream(document.getContent());
        FileRef orderFormFile = fileStorage.saveStream("supplier-order-form.docx", documentBytes);

        supplierOrderWithUpdatedStatus.setOrderForm(orderFormFile);

        dataManager.save(supplierOrderWithUpdatedStatus);

        return orderFormFile;
    }

}
