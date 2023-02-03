package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.PositionColor;
import io.jmix.bookstore.employee.PositionTranslation;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.employee.PositionColor.*;
import static io.jmix.bookstore.test_data.data_provider.employee.EmployeePositions.AvailablePosition.*;

@Component("bookstore_ProvisionDataProvider")
public class EmployeePositionDataProvider implements TestDataProvider<Position, EmployeePositionDataProvider.DataContext> {

    protected final DataManager dataManager;

    public EmployeePositionDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Position> create(DataContext dataContext) {
        return findPositionsOf(commit(createPositions()));
    }

    private List<Object> createPositions() {

        List<PositionData> positionData = List.of(
                new PositionData(
                        "IT Administrator",
                        Map.of(
                                Locale.GERMAN, "IT Administrator",
                                Locale.ENGLISH, "IT Administrator"
                        ),
                        IT_ADMINISTRATOR,
                        GREEN
                ),
                new PositionData(
                        "Procurement Specialist",
                        Map.of(
                                Locale.GERMAN, "Einkaufsmitarbeiter",
                                Locale.ENGLISH, "Procurement Specialist"
                        ),
                        PROCUREMENT_SPECIALIST,
                        PURPLE
                ),
                new PositionData(
                        "Procurement Manager",
                        Map.of(
                                Locale.GERMAN, "Einkaufsleiter",
                                Locale.ENGLISH, "Procurement Manager"
                        ),
                        PROCUREMENT_MANAGER,
                        DARK_PURPLE
                ),
                new PositionData(
                        "Order Fulfillment Specialist",
                        Map.of(
                                Locale.GERMAN, "Auftragsabwicklung",
                                Locale.ENGLISH, "Order Fulfillment Specialist"
                        ),
                        ORDER_FULFILLMENT_SPECIALIST,
                        RED
                ),
                new PositionData(
                        "Order Fulfillment Manager",
                        Map.of(
                                Locale.GERMAN, "Auftragsabwicklungleiter",
                                Locale.ENGLISH, "Order Fulfillment Manager"
                        ),
                        ORDER_FULFILLMENT_MANAGER,
                        DARK_RED
                ),
                new PositionData(
                        "Sales Representative",
                        Map.of(
                                Locale.GERMAN, "Vertriebsmitarbeiter",
                                Locale.ENGLISH, "Sales Representative"
                        ),
                        SALES_REPRESENTATIVE,
                        YELLOW
                )
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

        return Stream.concat(
                Stream.of(position),
                positionData.translations().entrySet()
                        .stream()
                        .map(entry -> createPositionTranslation(position, entry.getKey(), entry.getValue()))

        );
    }

    private PositionTranslation createPositionTranslation(Position position, Locale locale, String name) {
        PositionTranslation positionTranslation = dataManager.create(PositionTranslation.class);
        positionTranslation.setLocale(locale);
        positionTranslation.setName(name);
        positionTranslation.setPosition(position);
        return positionTranslation;
    }

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

    public record DataContext() {
    }

    record PositionData(String name, Map<Locale, String> translations,
                        EmployeePositions.AvailablePosition availablePosition, PositionColor color) {
    }
}
