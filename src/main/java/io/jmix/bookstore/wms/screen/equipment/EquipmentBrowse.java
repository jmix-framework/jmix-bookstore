package io.jmix.bookstore.wms.screen.equipment;

import io.jmix.bookstore.wms.entity.Equipment;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("wms_MqttEquipment.browse")
@UiDescriptor("equipment-browse.xml")
@LookupComponent("equipmentsTable")
public class EquipmentBrowse extends StandardLookup<Equipment> {
}