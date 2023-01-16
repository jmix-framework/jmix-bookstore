package io.jmix.bookstore.product.supplier.notification;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.notification.InAppNotificationSource;
import io.jmix.bookstore.notification.NotificationDetailDataProvider;
import io.jmix.bookstore.product.supplier.event.SupplierOrderDraftCreatedEvent;
import io.jmix.bookstore.product.supplier.event.SupplierOrderRequestCreatedEvent;
import io.jmix.bookstore.security.ProcurementSpecialistRole;
import io.jmix.core.Messages;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.security.role.assignment.RoleAssignment;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component("bookstore_SupplierOrderDraftCreatedNotificationDetailDataProvider")
public class SupplierOrderDraftCreatedNotificationDetailDataProvider implements NotificationDetailDataProvider<SupplierOrderDraftCreatedEvent> {

    protected final RoleAssignmentRepository roleAssignmentRepository;
    private final Messages messages;
    private final DatatypeFormatter datatypeFormatter;

    public SupplierOrderDraftCreatedNotificationDetailDataProvider(RoleAssignmentRepository roleAssignmentRepository, Messages messages, DatatypeFormatter datatypeFormatter) {
        this.roleAssignmentRepository = roleAssignmentRepository;
        this.messages = messages;
        this.datatypeFormatter = datatypeFormatter;
    }

    @Override
    public boolean supports(InAppNotificationSource event) {
        return event instanceof SupplierOrderDraftCreatedEvent;
    }

    @Override
    public NotificationDetailData provideData(SupplierOrderDraftCreatedEvent event) {
        String supplierName = event.supplierOrder().getSupplier().getName();
        String formattedOrderDate = datatypeFormatter.formatLocalDate(event.supplierOrder().getOrderDate());
        return new NotificationDetailData(
                usersWithRole(ProcurementSpecialistRole.CODE),
                messages.formatMessage(this.getClass(), "supplierOrderDraftCreated.subject", supplierName),
                messages.formatMessage(this.getClass(), "supplierOrderDraftCreated.body", supplierName, formattedOrderDate)
        );
    }

    private List<String> usersWithRole(String roleCode) {
        return roleAssignmentRepository.getAllAssignments().stream()
                .filter(roleAssignment -> roleAssignment.getRoleCode().equals(roleCode))
                .map(RoleAssignment::getUsername).toList();
    }
}
