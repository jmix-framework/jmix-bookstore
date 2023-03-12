package io.jmix.bookstore.wms.entity;

import io.jmix.bookstore.entity.StandardEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.NumberFormat;

import javax.persistence.*;

/**
 * Copyright © 深圳市树深计算机系统有限公司 版权所有
 * <p>
 * RFID设备MQTT订阅主题
 *
 * @author 周广明
 * v1 2023/3/12 03:23
 */
@JmixEntity
@Table(name = "WMS_MQTT_DEVICE", indexes = {
        @Index(name = "IDX_WMS_MQTT_DEVICE", columnList = "EQUIPMENT_ID")
})
@Entity(name = "wms_MqttDevice")
public class Device extends StandardEntity {

    @Column(name = "DEVICE_ID")
    private String deviceId;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @JoinColumn(name = "EQUIPMENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Equipment equipment;

    @Column(name = "STATUS")
    private Integer status = 0;


    @Column(name = "ONLINE_TIME")
    private String onlineTime;

    @Column(name = "WORKSPACE")
    private String workspace;

    @Column(name = "ENABLE")
    private Boolean enable = true;

    @Column(name = "DESCRIPTION")
    private String description;

    @NumberFormat(pattern = "0 'k/s'")
    @JmixProperty
    @Transient
    private Double up = 0.00;

    @NumberFormat(pattern = "0 'k/s'")
    @JmixProperty
    @Transient
    private Double down = 0.00;

    public void setUp(Double up) {
        this.up = up;
    }

    public Double getUp() {
        return up;
    }

    public void setDown(Double down) {
        this.down = down;
    }

    public Double getDown() {
        return down;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setStatus(Status status) {
        this.status = status == null ? null : status.getId();
    }

    public Status getStatus() {
        return status == null ? null : Status.fromId(status);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

}