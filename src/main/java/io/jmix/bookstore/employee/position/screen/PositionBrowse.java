package io.jmix.bookstore.employee.position.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Position;

@UiController("bookstore_Position.browse")
@UiDescriptor("position-browse.xml")
@LookupComponent("positionsTable")
public class PositionBrowse extends StandardLookup<Position> {

}
