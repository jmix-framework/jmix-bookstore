package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Position;

import java.util.List;

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
