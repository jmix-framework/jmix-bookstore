package io.jmix.bookstore.wms.screen.equipment;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.treedeep.upaas.core.context.TdContext;
import io.jmix.bookstore.wms.entity.Device;
import io.jmix.bookstore.wms.entity.Equipment;
import io.jmix.bookstore.wms.entity.Status;
import io.jmix.bookstore.wms.mqtt.MqttClientFactory;
import io.jmix.bookstore.wms.mqtt.TdMqttClient;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.*;
import io.jmix.ui.component.data.options.ListOptions;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.screen.*;
import io.jmix.ui.util.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static io.jmix.ui.util.OperationResult.Status.SUCCESS;

@UiController("wms_MqttEquipment.edit")
@UiDescriptor("equipment-edit.xml")
@EditedEntityContainer("equipmentDc")
public class EquipmentEdit extends StandardEditor<Equipment> {

    @Autowired
    private CollectionPropertyContainer<Device> devicesDc;

    @Autowired
    private Notifications notifications;

    @Autowired
    private ComboBox<Boolean> cleanStartField;

    @Autowired
    private Form mqttClient;

    @Autowired
    private Button connection;

    @Autowired
    private TextField<String> onlineTimeField;

    @Autowired
    private Timer deviceTimer;

    @Autowired
    private Timer equipmentTimer;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Equipment> event) {
        // 初始化一些值
        Equipment entity = event.getEntity();
        entity.setClientId("wms_" + TdContext.getCurrentUserTenantId() + "_" + System.currentTimeMillis());
        entity.setCleanStart(true);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        // 添加cleanStartField操作选项
        cleanStartField.setOptions(ListOptions.of(Boolean.TRUE, Boolean.FALSE));

        // 设备运行时监控开启
        deviceTimer.start();
    }

    @Subscribe("contentTabSheet")
    public void onContentTabSheetSelectedTabChange(TabSheet.SelectedTabChangeEvent event) {
        TabSheet.Tab selectedTab = event.getSelectedTab();

        // 切换tab2时的操作
        if ("tab2".equals(selectedTab.getName())) {

            Equipment entity = getEditedEntity();
            if (entity.getStatus().equals(Status.ONLINE)) {
                // 设置连接图标
                connection.setIcon("font-icon:HEART");
                mqttClient.setEditable(false);

                // 监控
                equipmentTimer.start();
            } else {
                equipmentTimer.stop();
            }

            deviceTimer.stop();

        } else {
            deviceTimer.start();
            equipmentTimer.stop();
        }

    }


    @Subscribe
    public void onBeforeClose(BeforeCloseEvent event) {

    }

    @Subscribe("connection")
    public void onConnectionClick(Button.ClickEvent event) throws Exception {

        OperationResult operationResult = commitChanges();
        if (!operationResult.getStatus().equals(SUCCESS)) {
            return;
        }

        Equipment equipment = getEditedEntity();

        if (equipment.getStatus().equals(Status.ONLINE)) {
            notifications
                    .create(Notifications.NotificationType.WARNING)
                    .withCaption("设备组")
                    .withDescription("MQTT 服务已连接！")
                    .show();
            return;
        }

        TdMqttClient tdMqttClient = MqttClientFactory.create(equipment);

        notifications
                .create(Notifications.NotificationType.TRAY)
                .withCaption("设备组")
                .withDescription("WMS 连接 MQTT 服务成功。")
                .show();

        // 禁止编辑连接信息、设置连接图标
        mqttClient.setEditable(false);
        event.getSource().setIcon("font-icon:HEART");

        // 修改状态
        equipment.setStatus(Status.ONLINE);
        equipment.setClientStartTime(new Date());

        // 保存状态
        commitChanges();

        // 重置设备组运行计时
        onlineTimeField.setValue("");
        equipmentTimer.start();

    }

    @Subscribe("disconnect")
    public void onDisconnectClick(Button.ClickEvent event) {

        mqttClient.setEditable(true);
        connection.setIcon("font-icon:HEART_O");
        equipmentTimer.stop();

        notifications
                .create(Notifications.NotificationType.WARNING)
                .withCaption("设备组")
                .withDescription("WMS 已断开 MQTT 服务。")
                .show();
        Equipment equipment = getEditedEntity();

        if (equipment.getStatus().equals(Status.OFFLINE)) {
            return;
        }

        // 修改状态
        String clientId = equipment.getClientId();
        MqttClientFactory.remove(clientId);
        equipment.setStatus(Status.OFFLINE);
        equipment.setClientStartTime(null);

        // 保存状态
        commitChanges();

    }


    @Subscribe("deviceTimer")
    public void onTaskDeviceTimerAction(Timer.TimerActionEvent event) {
        // todo 设备列表里的设备状态监控
    }

    @Subscribe("equipmentTimer")
    public void onTaskEquipmentTimerAction(Timer.TimerActionEvent event) {
        onlineTimeField.setValue(getOnlineTime(getEditedEntity().getClientStartTime()));
    }

    private String getOnlineTime(Date startDate) {
        if (startDate == null) {
            return null;
        }
        long millis = DateUtil.between(startDate, new Date(), DateUnit.MS);
        return DateUtil.formatBetween(millis, BetweenFormatter.Level.SECOND);
    }

}