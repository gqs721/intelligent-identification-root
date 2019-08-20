package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class DeviceConfig {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Integer userId;

    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer streamMediaId;

    /**
     * 设备号
     */
    @ApiModelProperty(value="设备号")
    private String deviceNumber;

    /**
     * 设备通道号
     */
    @ApiModelProperty(value="设备通道号")
    private String deviceChannel;

    /**
     * 设备名称
     */
    @ApiModelProperty(value="设备名称")
    private String deviceName;

    /**
     * 设备账号
     */
    @ApiModelProperty(value="设备账号")
    private String deviceAccount;

    /**
     * 设备密码
     */
    @ApiModelProperty(value="设备密码")
    private String devicePassword;

    /**
     * 厂商
     */
    @ApiModelProperty(value="厂商")
    private String manufacturer;

    /**
     * 分辨率
     */
    @ApiModelProperty(value="分辨率")
    private String resolutionRatio;

    /**
     * 访问地址
     */
    @ApiModelProperty(value="访问地址")
    private String visitAddress;

    /**
     * 识别类型
     */
    @ApiModelProperty(value="识别类型")
    private String distinguishType;

    /**
     * 服务器ip
     */
    @ApiModelProperty(value="服务器ip")
    private String serverIp;

    /**
     * sip服务器ip
     */
    @ApiModelProperty(value="sip服务器ip")
    private String sipServerIp;

    /**
     * 状态，0：启用，1：禁用
     */
    @ApiModelProperty(value="状态，0：启用，1：禁用")
    private Integer status;

    private String statusStr;

    /**
     * 删除状态，0：未删除，1：已删除
     */
    @ApiModelProperty(value="删除状态，0：未删除，1：已删除")
    private Integer delStatus;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStreamMediaId() {
        return streamMediaId;
    }

    public void setStreamMediaId(Integer streamMediaId) {
        this.streamMediaId = streamMediaId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber == null ? null : deviceNumber.trim();
    }

    public String getDeviceChannel() {
        return deviceChannel;
    }

    public void setDeviceChannel(String deviceChannel) {
        this.deviceChannel = deviceChannel == null ? null : deviceChannel.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceAccount() {
        return deviceAccount;
    }

    public void setDeviceAccount(String deviceAccount) {
        this.deviceAccount = deviceAccount == null ? null : deviceAccount.trim();
    }

    public String getDevicePassword() {
        return devicePassword;
    }

    public void setDevicePassword(String devicePassword) {
        this.devicePassword = devicePassword == null ? null : devicePassword.trim();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    public String getResolutionRatio() {
        return resolutionRatio;
    }

    public void setResolutionRatio(String resolutionRatio) {
        this.resolutionRatio = resolutionRatio == null ? null : resolutionRatio.trim();
    }

    public String getVisitAddress() {
        return visitAddress;
    }

    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress == null ? null : visitAddress.trim();
    }

    public String getDistinguishType() {
        return distinguishType;
    }

    public void setDistinguishType(String distinguishType) {
        this.distinguishType = distinguishType == null ? null : distinguishType.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public String getSipServerIp() {
        return sipServerIp;
    }

    public void setSipServerIp(String sipServerIp) {
        this.sipServerIp = sipServerIp == null ? null : sipServerIp.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", streamMediaId=").append(streamMediaId);
        sb.append(", deviceNumber=").append(deviceNumber);
        sb.append(", deviceChannel=").append(deviceChannel);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", deviceAccount=").append(deviceAccount);
        sb.append(", devicePassword=").append(devicePassword);
        sb.append(", manufacturer=").append(manufacturer);
        sb.append(", resolutionRatio=").append(resolutionRatio);
        sb.append(", visitAddress=").append(visitAddress);
        sb.append(", distinguishType=").append(distinguishType);
        sb.append(", serverIp=").append(serverIp);
        sb.append(", sipServerIp=").append(sipServerIp);
        sb.append(", status=").append(status);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}