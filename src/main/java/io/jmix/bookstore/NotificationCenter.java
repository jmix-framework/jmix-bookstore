package io.jmix.bookstore;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.supplier.SupplierOrderRequestCreatedEvent;
import io.jmix.bookstore.security.ProcurementSpecialistRole;
import io.jmix.core.Messages;
import io.jmix.core.security.Authenticated;
import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.entity.ContentType;
import io.jmix.security.role.assignment.RoleAssignment;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_NotificationCenter")
public class NotificationCenter {

    protected final NotificationManager notificationManager;
    protected final RoleAssignmentRepository roleAssignmentRepository;
    @Autowired
    private Messages messages;

    public NotificationCenter(NotificationManager notificationManager, RoleAssignmentRepository roleAssignmentRepository) {
        this.notificationManager = notificationManager;
        this.roleAssignmentRepository = roleAssignmentRepository;
    }

    @Async
    @Authenticated
    @EventListener
    public void onSupplierOrderCreatedEvent(SupplierOrderRequestCreatedEvent event) {
        User requestedBy = event.request().getRequestedBy();
        notificationManager.createNotification()
                .withSubject(messages.formatMessage(this.getClass(), "supplierOrderCreated.subject", event.productName()))
                .withRecipientUsernames(usersWithRole(ProcurementSpecialistRole.CODE))
                .toChannelsByNames("in-app")
                .withContentType(ContentType.PLAIN)
                .withBody(messages.formatMessage(this.getClass(), "supplierOrderCreated.body", event.productName(), requestedBy.getDisplayName()))
                .send();
    }

    private List<String> usersWithRole(String roleCode) {
        return roleAssignmentRepository.getAllAssignments()
                .stream().filter(roleAssignment -> roleAssignment.getRoleCode().equals(roleCode))
                .map(RoleAssignment::getUsername).toList();
    }
}
