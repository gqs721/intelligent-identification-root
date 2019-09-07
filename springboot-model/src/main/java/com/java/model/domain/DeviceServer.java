package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class DeviceServer {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 设备id
     */
    @ApiModelProperty(value="设备id")
    private int deviceId;

    /**
     * 服务器id
     */
    @ApiModelProperty(value="服务器id")
    private int serverId;

    /**
     * 设备类型
     */
    @ApiModelProperty(value="设备类型")
    private int deviceType;

    /**
     * "删除状态，0：未删除，1：已删除
     */
    @ApiModelProperty(value="\"删除状态，0：未删除，1：已删除")
    private int delStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", serverId=").append(serverId);
        sb.append(", deviceType=").append(deviceType);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}