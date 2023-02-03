package io.jmix.bookstore.emploee.screen.positiontranslation;

import io.jmix.core.MessageTools;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.PositionTranslation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@UiController("bookstore_PositionTranslation.edit")
@UiDescriptor("position-translation-edit.xml")
@EditedEntityContainer("positionTranslationDc")
public class PositionTranslationEdit extends StandardEditor<PositionTranslation> {


    @Autowired
    private MessageTools messageTools;
    @Autowired
    private ComboBox<Locale> localeField;

    @Subscribe
    public void onInit(InitEvent event) {
        initLocaleField();
    }



    private void initLocaleField() {
        localeField.setOptionsMap(messageTools.getAvailableLocalesMap());
    }
}
