package io.jmix.bookstore.product.supplier.bpm;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.core.FileRef;

/**
 * Service used by the BPMN process 'perform-supplier-order'.
 * It provides the service implementations for the corresponding services tasks in the process.
 */
public interface PerformSupplierOrderService {

    String NAME = "bookstore_PerformSupplierOrderService";

    /**
     * Notifies the requester about a declined order through the BPM process.
     * @param supplierOrder the supplier order that was declined
     */
    @SuppressWarnings("UnusedMethod")
    void notifyRequestersAboutInvalidRequest(SupplierOrder supplierOrder);

    /**
     * places a supplier order through the BPM process
     * @param supplierOrder the supplier order to place
     */
    @SuppressWarnings("UnusedMethod")
    FileRef placeSupplierOrder(SupplierOrder supplierOrder, User reviewedBy);
}
