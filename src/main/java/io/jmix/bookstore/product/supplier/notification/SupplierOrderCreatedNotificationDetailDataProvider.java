package io.jmix.bookstore.product.supplier.notification;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.notification.NotificationDetailDataProvider;
import io.jmix.bookstore.notification.InAppNotificationSource;
import io.jmix.bookstore.product.supplier.SupplierOrderRequestCreatedEvent;
import io.jmix.bookstore.security.ProcurementSpecialistRole;
import io.jmix.core.Messages;
import io.jmix.security.role.assignment.RoleAssignment;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_SupplierOrderCreatedNotificationDetailDataProvider")
public class SupplierOrderCreatedNotificationDetailDataProvider implements NotificationDetailDataProvider<SupplierOrderRequestCreatedEvent> {

    protected final RoleAssignmentRepository roleAssignmentRepository;
    private final Messages messages;

    public SupplierOrderCreatedNotificationDetailDataProvider(RoleAssignmentRepository roleAssignmentRepository, Messages messages) {
        this.roleAssignmentRepository = roleAssignmentRepository;
        this.messages = messages;
    }

    @Override
    public boolean supports(InAppNotificationSource event) {
        return event instanceof SupplierOrderRequestCreatedEvent;
    }

    @Override
    public NotificationDetailData provideData(SupplierOrderRequestCreatedEvent event) {
        User requestedBy = event.request().getRequestedBy();
        return new NotificationDetailData(
                usersWithRole(ProcurementSpecialistRole.CODE),
                messages.formatMessage(this.getClass(), "supplierOrderCreated.subject", event.productName()),
                messages.formatMessage(this.getClass(), "supplierOrderCreated.body", event.productName(), requestedBy.getDisplayName())
        );
    }

    private List<String> usersWithRole(String roleCode) {
        return roleAssignmentRepository.getAllAssignments().stream()
                .filter(roleAssignment -> roleAssignment.getRoleCode().equals(roleCode))
                .map(RoleAssignment::getUsername).toList();
    }
}
