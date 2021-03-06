package com.java.model.domain;

import java.util.Date;

public class PushConfig {

    private Integer id;

    private Integer deviceId;

    private Integer serverId;

    private Integer pushInterval;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getPushInterval() {
        return pushInterval;
    }

    public void setPushInterval(Integer pushInterval) {
        this.pushInterval = pushInterval;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
