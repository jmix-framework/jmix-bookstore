package io.jmix.bookstore.wms.entity;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.Secret;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

/**
 * Copyright © 深圳市树深计算机系统有限公司 版权所有
 * <p>
 * 设备组（MQTT客户端）
 *
 * @author 周广明
 * v1 2023/3/12 03:24
 */
@JmixEntity
@Table(name = "WMS_MQTT_EQUIPMENT")
@Entity(name = "wms_MqttEquipment")
public class Equipment extends StandardTenantEntity {

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "SUBSCRIPTION")
    @Lob
    private String subscription;

    @Column(name = "PUBLISH")
    @Lob
    private String publish;

    @Column(name = "STATUS")
    private Integer status = 0;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLE")
    private Boolean enable = false;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "equipment")
    private List<Device> devices;

    @OnDelete(DeletePolicy.DENY)
    @OneToMany(mappedBy = "equipment")
    private List<EquipmentHistory> history;

    @Column(name = "MQTT_HOST")
    private String host = "110.41.157.159";

    @NumberFormat(pattern = "0")
    @Column(name = "MQTT_PORT")
    private Integer port = 1883;

    @Column(name = "MQTT_PATH")
    private String path = "/mqtt";

    @Min(message = "{msg://io.jmix.bookstore.wms.entity/RfidEquipment.sessionExpiryInterval.validation.Min}", value = 0)
    @Column(name = "MQTT_KEEPALIVE")
    private Integer keepalive = 60;

    @Min(message = "{msg://io.jmix.bookstore.wms.entity/RfidEquipment.sessionExpiryInterval.validation.Min}", value = 0)
    @Column(name = "MQTT_SESSION_EXPIRY_INTERVAL")
    private Long sessionExpiryInterval = 0L;

    @Column(name = "MQTT_TLS")
    private Boolean tls = false;

    @Column(name = "CLEAN_START")
    private Boolean cleanStart = true;

    @Column(name = "MQTT_PROTOCOL_VERSION")
    private Integer protocolVersion = 5;

    @Column(name = "MQTT_USERNAME")
    private String username;

    @Secret
    @Column(name = "MQTT_PASSWORD")
    private String password;

    @Column(name = "MQTT_CLIENT_ID")
    private String clientId;

    @Column(name = "CLIENT_START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date clientStartTime;

    @Column(name = "QOS")
    private Integer qos = 0;

    @JmixProperty
    @Transient
    private String onlineTime;

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getSubscription() {
        return subscription;
    }

    public Date getClientStartTime() {
        return clientStartTime;
    }

    public void setClientStartTime(Date clientStartTime) {
        this.clientStartTime = clientStartTime;
    }

    public void setSessionExpiryInterval(Long sessionExpiryInterval) {
        this.sessionExpiryInterval = sessionExpiryInterval;
    }

    public Long getSessionExpiryInterval() {
        return sessionExpiryInterval;
    }

    public void setCleanStart(Boolean cleanStart) {
        this.cleanStart = cleanStart;
    }

    public Boolean getCleanStart() {
        return cleanStart;
    }

    public void setStatus(Status status) {
        this.status = status == null ? null : status.getId();
    }

    public Status getStatus() {
        return status == null ? null : Status.fromId(status);
    }

    public List<EquipmentHistory> getHistory() {
        return history;
    }

    public void setHistory(List<EquipmentHistory> history) {
        this.history = history;
    }

    public void setQos(QoS qos) {
        this.qos = qos == null ? null : qos.getId();
    }

    public QoS getQos() {
        return qos == null ? null : QoS.fromId(qos);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setKeepalive(Integer keepalive) {
        this.keepalive = keepalive;
    }

    public void setTls(Boolean tls) {
        this.tls = tls;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Integer getKeepalive() {
        return keepalive;
    }

    public Boolean getTls() {
        return tls;
    }

    public void setProtocolVersion(MqttProtocol protocolVersion) {
        this.protocolVersion = protocolVersion == null ? null : protocolVersion.getId();
    }

    public MqttProtocol getProtocolVersion() {
        return protocolVersion == null ? null : MqttProtocol.fromId(protocolVersion);
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getClientId() {
        return clientId;
    }


    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}