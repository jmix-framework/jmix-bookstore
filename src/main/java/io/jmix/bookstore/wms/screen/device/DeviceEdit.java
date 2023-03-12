package io.jmix.bookstore.wms.screen.device;

import io.jmix.bookstore.wms.entity.Device;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("wms_MqttDevice.edit")
@UiDescriptor("device-edit.xml")
@EditedEntityContainer("deviceDc")
public class DeviceEdit extends StandardEditor<Device> {
}