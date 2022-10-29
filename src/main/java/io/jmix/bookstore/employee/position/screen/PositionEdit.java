package io.jmix.bookstore.employee.position.screen;

import io.jmix.bookstore.employee.PositionColor;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Position;

@UiController("bookstore_Position.edit")
@UiDescriptor("position-edit.xml")
@EditedEntityContainer("positionDc")
public class PositionEdit extends StandardEditor<Position> {

}
