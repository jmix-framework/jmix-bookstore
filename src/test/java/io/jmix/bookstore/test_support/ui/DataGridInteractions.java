package io.jmix.bookstore.test_support.ui;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.Screen;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DataGridInteractions<E> {

    private final DataGrid<E> dataGrid;

    public DataGridInteractions(DataGrid<E> dataGrid) {
        this.dataGrid = dataGrid;
    }

    public static <E> DataGridInteractions<E> of(DataGrid<E> table, Class<E> entityClass) {
        return new DataGridInteractions<>(table);
    }

    public static <E, S extends Screen> DataGridInteractions<E> of(S screen, Class<E> entityClass, String componentId) {
        return DataGridInteractions.of(
                (DataGrid) screen.getWindow().getComponent(componentId),
                entityClass
        );
    }

    @Nullable
    Button button(String buttonId) {
        return Optional.ofNullable((Button) dataGrid.getButtonsPanel().getComponent(buttonId)).orElseThrow();
    }

    public E firstItem() {
        return dataGrid.getItems().getItems().findFirst().orElseThrow();
    }

    public void selectFirst() {
        dataGrid.setSelected(firstItem());
    }

    public void edit(E entity) {
        dataGrid.setSelected(entity);
        button("editBtn").click();
    }

    public void create() {
        dataGrid.getActionNN("create").actionPerform(null);
    }
}
