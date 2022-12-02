package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;

import java.util.List;
import java.util.stream.Collectors;

public record EmployeePositions(List<Position> positions) {
    public int size() {
        return positions().size();
    }

    public Position find(AvailablePosition availablePosition) {
        return positions.stream()
                .filter(position -> availablePosition.getCode().equals(position.getCode()))
                .findFirst()
                .orElseThrow();
    }

    public List<Employee> findEmployeesWith(List<Employee> allEmployees, AvailablePosition availablePosition) {
        Position position = find(availablePosition);

        return allEmployees.stream()
                .filter(employee -> position.equals(employee.getPosition()))
                .collect(Collectors.toList());
    }


    public enum AvailablePosition {
        IT_ADMINISTRATOR("it-administrator"),
        PROCUREMENT_SPECIALIST("procurement-specialist"),
        PROCUREMENT_MANAGER("procurement-manager"),
        ORDER_FULFILLMENT_SPECIALIST("order-fulfillment-specialist"),
        ORDER_FULFILLMENT_MANAGER("order-fulfillment-manager"),
        SALES_REPRESENTATIVE("sales-representative");

        private final String code;

        AvailablePosition(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

}
