package io.jmix.bookstore.employee.position.screen;

import io.jmix.bookstore.employee.PositionColor;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Position;

@UiController("bookstore_Position.edit")
@UiDescriptor("position-edit.xml")
@EditedEntityContainer("positionDc")
@Route(value = "positions/edit", parentPrefix = "positions")
public class PositionEdit extends StandardEditor<Position> {

}
