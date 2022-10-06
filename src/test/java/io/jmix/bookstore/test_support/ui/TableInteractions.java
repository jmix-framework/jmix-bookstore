package io.jmix.bookstore.test_support.ui;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.Screen;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TableInteractions<E> {

    private final Table<E> table;

    public TableInteractions(Table<E> table) {
        this.table = table;
    }

    public static <E> TableInteractions<E> of(Table<E> table, Class<E> entityClass) {
        return new TableInteractions<>(table);
    }

    public static <E, S extends Screen> TableInteractions<E> of(S screen, Class<E> entityClass, String componentId) {
        return TableInteractions.of(
                (Table) screen.getWindow().getComponent(componentId),
                entityClass
        );
    }

    @Nullable
    Button button(String buttonId) {
        return Optional.ofNullable((Button) table.getButtonsPanel().getComponent(buttonId)).orElseThrow();
    }

    public E firstItem() {
        return table.getItems().getItems().stream().findFirst().orElseThrow();
    }

    public void selectFirst() {
        table.setSelected(firstItem());
    }

    public void edit(E entity) {
        table.setSelected(entity);
        button("editBtn").click();
    }

    public void create() {
        table.getActionNN("create").actionPerform(null);
    }
}
