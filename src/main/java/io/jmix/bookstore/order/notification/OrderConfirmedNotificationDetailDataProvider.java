package io.jmix.bookstore.order.notification;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.notification.NotificationDetailDataProvider;
import io.jmix.bookstore.notification.InAppNotificationSource;
import io.jmix.bookstore.order.OrderConfirmedEvent;
import io.jmix.bookstore.test_data.data_provider.employee.EmployeePositions;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Messages;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("bookstore_OrderConfirmedNotificationRecipientResolver")
public class OrderConfirmedNotificationDetailDataProvider implements NotificationDetailDataProvider<OrderConfirmedEvent> {

    private final DataManager dataManager;
    private final Messages messages;

    public OrderConfirmedNotificationDetailDataProvider(DataManager dataManager, Messages messages) {
        this.dataManager = dataManager;
        this.messages = messages;
    }

    @Override
    public boolean supports(InAppNotificationSource event) {
        return event instanceof OrderConfirmedEvent;
    }

    @Override
    public NotificationDetailData provideData(OrderConfirmedEvent event) {
        return new NotificationDetailData(
                toUsernames(associatedSalesRepresentatives(event.order().getShippingAddress())),
                messages.formatMessage(this.getClass(), "orderConfirmed.subject", event.order().getCustomer().getFullName()),
                messages.formatMessage(this.getClass(), "orderConfirmed.body", event.order().getCustomer().getFullName())
        );
    }


    private List<Employee> associatedSalesRepresentatives(Address orderAddress) {
        List<Employee> salesRepresentatives = employeesWithPosition(EmployeePositions.AvailablePosition.SALES_REPRESENTATIVE);

        return salesRepresentatives.stream()
                .filter(employee -> employee.getTerritories().stream().anyMatch(territory -> territory.getGeographicalArea().contains(orderAddress.getPosition())))
                .toList();
    }


    private List<String> toUsernames(List<Employee> employees) {
        return employees.stream()
                .map(this::toUsername)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<String> toUsername(Employee employee) {
        return Optional.ofNullable(employee.getUser())
                .map(User::getUsername);
    }


    private List<Employee> employeesWithPosition(EmployeePositions.AvailablePosition position) {
        List<Employee> employees = dataManager.load(Employee.class).all().fetchPlan(employee -> {
            employee.addFetchPlan(FetchPlan.BASE);
            employee.add("territories", FetchPlan.BASE);
            employee.add("user", FetchPlan.BASE);
        }).list();
        List<Position> positions = dataManager.load(Position.class).all().list();

        return new EmployeePositions(positions).findEmployeesWith(employees, position);
    }
}
