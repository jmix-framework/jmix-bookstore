package io.jmix.bookstore.employee.position.screen;

import io.jmix.bookstore.employee.PositionColor;
import io.jmix.bookstore.employee.PositionTranslation;
import io.jmix.core.MessageTools;
import io.jmix.core.MetadataTools;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Position;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@UiController("bookstore_Position.edit")
@UiDescriptor("position-edit.xml")
@EditedEntityContainer("positionDc")
@Route(value = "positions/edit", parentPrefix = "positions")
public class PositionEdit extends StandardEditor<Position> {
    @Autowired
    private MessageTools messageTools;

    @Install(to = "translationsTable.locale", subject = "formatter")
    private String translationsTableLocaleFormatter(Locale value) {
        return messageTools.getLocaleDisplayName(value);
    }

}
