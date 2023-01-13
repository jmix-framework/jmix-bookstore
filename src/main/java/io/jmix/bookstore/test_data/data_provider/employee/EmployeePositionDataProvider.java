package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.PositionColor;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("bookstore_ProvisionDataProvider")
public class EmployeePositionDataProvider implements TestDataProvider<Position, EmployeePositionDataProvider.DataContext> {

    protected final DataManager dataManager;

    public record DataContext(){}
    public EmployeePositionDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Position> create(DataContext dataContext) {
        return findPositionsOf(commit(createPositions()));
    }

    private List<Object> createPositions() {

        List<PositionData> positionData = List.of(
                new PositionData("IT Administrator", EmployeePositions.AvailablePosition.IT_ADMINISTRATOR, PositionColor.GREEN),
                new PositionData("Procurement Specialist", EmployeePositions.AvailablePosition.PROCUREMENT_SPECIALIST, PositionColor.PURPLE),
                new PositionData("Procurement Manager", EmployeePositions.AvailablePosition.PROCUREMENT_MANAGER, PositionColor.DARK_PURPLE),
                new PositionData("Order Fulfillment Specialist", EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_SPECIALIST, PositionColor.RED),
                new PositionData("Order Fulfillment Manager", EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_MANAGER, PositionColor.DARK_RED),
                new PositionData("Sales Representative", EmployeePositions.AvailablePosition.SALES_REPRESENTATIVE, PositionColor.YELLOW)
        );

        return positionData.stream()
                .flatMap(this::toEntities)
                .collect(Collectors.toList());
    }

    private Stream<Object> toEntities(PositionData positionData) {
        Position position = dataManager.create(Position.class);
        position.setName(positionData.name());
        position.setCode(positionData.availablePosition().getCode());
        position.setColor(positionData.color());

        return Stream.of(position);
    }


    record PositionData(String name, EmployeePositions.AvailablePosition availablePosition, PositionColor color) {}

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        saveContext.setDiscardSaved(true);
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }
    private List<Position> findPositionsOf(List<Object> storedEntities) {
        return storedEntities.stream()
                .filter(o -> o instanceof Position)
                .map(user -> (Position) user)
                .collect(Collectors.toList());
    }
}
