package io.jmix.bookstore.wms.screen.device;

import io.jmix.bookstore.wms.entity.Device;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("wms_MqttDevice.browse")
@UiDescriptor("device-browse.xml")
@LookupComponent("devicesTable")
public class DeviceBrowse extends StandardLookup<Device> {
}