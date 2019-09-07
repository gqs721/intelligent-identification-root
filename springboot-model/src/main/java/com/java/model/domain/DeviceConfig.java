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
     * 流媒体id
     */
    @ApiModelProperty(value="流媒体id")
    private Integer streamMediaId;

    /**
     * NVRid
     */
    @ApiModelProperty(value="NVRid")
    private Integer nvrId;

    /**
     * 分组id
     */
    @ApiModelProperty(value="分组id")
    private Integer groupId;

    /**
     * 识别模型服务器id
     */
    @ApiModelProperty(value="识别模型服务器id，多个用,分隔")
    private String serverIds;

    /**
     * 设备号
     */
    @ApiModelProperty(value="设备号")
    private String deviceNumber;

    /**
     * 设备通道
     */
    @ApiModelProperty(value="设备通道")
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
     * 型号
     */
    @ApiModelProperty(value="型号")
    private String deviceModel;

    /**
     * 分辨率
     */
    @ApiModelProperty(value="分辨率")
    private String resolutionRatio;

    /**
     * 码率
     */
    @ApiModelProperty(value="码率")
    private Integer codeRate;

    /**
     * 访问地址
     */
    @ApiModelProperty(value="访问地址")
    private String visitAddress;

    /**
     * 访问端口
     */
    @ApiModelProperty(value="访问端口")
    private Integer visitPort;

    /**
     * 识别类型
     */
    @ApiModelProperty(value="识别类型")
    private String distinguishType;

    /**
     * 设备类型，1：网络摄像头，2：智能眼镜
     */
    @ApiModelProperty(value="设备类型，1：网络摄像头，2：智能眼镜")
    private Integer deviceType;

    /**
     * 存储类型，0：无，1：存储服务器，2：NVR设备
     */
    @ApiModelProperty(value="存储类型，0：无，1：存储服务器，2：NVR设备")
    private Integer storageType;

    /**
     * 流媒体服务ip
     */
    @ApiModelProperty(value="流媒体服务ip")
    private String sipServerIp;

    /**
     * NVRid
     */
    @ApiModelProperty(value="NVRip")
    private String nvrIp;

    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private String deviceLongitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private String deviceLatitude;

    /**
     * 在线状态
     */
    @ApiModelProperty(value="在线状态,列表显示，无需添加，默认离线")
    private String onlineStatus;

    /**
     * 状态，0：启用，1：禁用
     */
    @ApiModelProperty(value="状态，0：启用，1：禁用")
    private Integer status;

    @ApiModelProperty(value="列表显示，无需添加")
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

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

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

    public Integer getNvrId() {
        return nvrId;
    }

    public void setNvrId(Integer nvrId) {
        this.nvrId = nvrId;
    }

    public String getServerIds() {
        return serverIds;
    }

    public void setServerIds(String serverIds) {
        this.serverIds = serverIds;
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

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel == null ? null : deviceModel.trim();
    }

    public String getResolutionRatio() {
        return resolutionRatio;
    }

    public void setResolutionRatio(String resolutionRatio) {
        this.resolutionRatio = resolutionRatio == null ? null : resolutionRatio.trim();
    }

    public Integer getCodeRate() {
        return codeRate;
    }

    public void setCodeRate(Integer codeRate) {
        this.codeRate = codeRate;
    }

    public String getVisitAddress() {
        return visitAddress;
    }

    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress == null ? null : visitAddress.trim();
    }

    public Integer getVisitPort() {
        return visitPort;
    }

    public void setVisitPort(Integer visitPort) {
        this.visitPort = visitPort;
    }

    public String getDistinguishType() {
        return distinguishType;
    }

    public void setDistinguishType(String distinguishType) {
        this.distinguishType = distinguishType == null ? null : distinguishType.trim();
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getSipServerIp() {
        return sipServerIp;
    }

    public void setSipServerIp(String sipServerIp) {
        this.sipServerIp = sipServerIp == null ? null : sipServerIp.trim();
    }

    public String getNvrIp() {
        return nvrIp;
    }

    public void setNvrIp(String nvrIp) {
        this.nvrIp = nvrIp == null ? null : nvrIp.trim();
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public String getDeviceLongitude() {
        return deviceLongitude;
    }

    public void setDeviceLongitude(String deviceLongitude) {
        this.deviceLongitude = deviceLongitude;
    }

    public String getDeviceLatitude() {
        return deviceLatitude;
    }

    public void setDeviceLatitude(String deviceLatitude) {
        this.deviceLatitude = deviceLatitude;
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
        sb.append(", nvrId=").append(nvrId);
        sb.append(", groupId=").append(groupId);
        sb.append(", deviceNumber=").append(deviceNumber);
        sb.append(", deviceChannel=").append(deviceChannel);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", deviceAccount=").append(deviceAccount);
        sb.append(", devicePassword=").append(devicePassword);
        sb.append(", manufacturer=").append(manufacturer);
        sb.append(", deviceModel=").append(deviceModel);
        sb.append(", resolutionRatio=").append(resolutionRatio);
        sb.append(", codeRate=").append(codeRate);
        sb.append(", visitAddress=").append(visitAddress);
        sb.append(", visitPort=").append(visitPort);
        sb.append(", distinguishType=").append(distinguishType);
        sb.append(", deviceType=").append(deviceType);
        sb.append(", storageType=").append(storageType);
        sb.append(", sipServerIp=").append(sipServerIp);
        sb.append(", nvrIp=").append(nvrIp);
        sb.append(", deviceLongitude=").append(deviceLongitude);
        sb.append(", deviceLatitude=").append(deviceLatitude);
        sb.append(", onlineStatus=").append(onlineStatus);
        sb.append(", status=").append(status);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}