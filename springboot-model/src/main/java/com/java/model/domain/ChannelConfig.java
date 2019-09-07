package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ChannelConfig {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * NVR_id
     */
    @ApiModelProperty(value="NVR_id")
    private Integer nvrId;

    /**
     * 设备id
     */
    @ApiModelProperty(value="设备id")
    private Integer deviceId;

    /**
     * 流媒体id
     */
    @ApiModelProperty(value="流媒体id")
    private Integer sipId;

    /**
     * NVR编号
     */
    @ApiModelProperty(value="NVR编号")
    private String nvrNumber;

    /**
     * 通道编号
     */
    @ApiModelProperty(value="通道编号")
    private String channelNumber;

    /**
     * 通道名称
     */
    @ApiModelProperty(value="通道名称")
    private String channelName;

    /**
     * 账号
     */
    @ApiModelProperty(value="账号")
    private String channelAccount;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String channelPassword;

    /**
     * 厂商
     */
    @ApiModelProperty(value="厂商")
    private String manufacturer;

    /**
     * 型号
     */
    @ApiModelProperty(value="型号")
    private String channelModel;

    /**
     * ip
     */
    @ApiModelProperty(value="ip")
    private String channelIp;

    /**
     * 端口
     */
    @ApiModelProperty(value="端口")
    private Integer channelPort;

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
     * 识别类型
     */
    @ApiModelProperty(value="识别类型")
    private String distinguishType;

    /**
     * 流媒体ip
     */
    @ApiModelProperty(value="流媒体ip")
    private String sipIp;

    /**
     * NVRip
     */
    @ApiModelProperty(value="NVRip")
    private String nvrIp;

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

    public Integer getNvrId() {
        return nvrId;
    }

    public void setNvrId(Integer nvrId) {
        this.nvrId = nvrId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSipId() {
        return sipId;
    }

    public void setSipId(Integer sipId) {
        this.sipId = sipId;
    }

    public String getNvrNumber() {
        return nvrNumber;
    }

    public void setNvrNumber(String nvrNumber) {
        this.nvrNumber = nvrNumber == null ? null : nvrNumber.trim();
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber == null ? null : channelNumber.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getChannelAccount() {
        return channelAccount;
    }

    public void setChannelAccount(String channelAccount) {
        this.channelAccount = channelAccount == null ? null : channelAccount.trim();
    }

    public String getChannelPassword() {
        return channelPassword;
    }

    public void setChannelPassword(String channelPassword) {
        this.channelPassword = channelPassword == null ? null : channelPassword.trim();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    public String getChannelModel() {
        return channelModel;
    }

    public void setChannelModel(String channelModel) {
        this.channelModel = channelModel == null ? null : channelModel.trim();
    }

    public String getChannelIp() {
        return channelIp;
    }

    public void setChannelIp(String channelIp) {
        this.channelIp = channelIp == null ? null : channelIp.trim();
    }

    public Integer getChannelPort() {
        return channelPort;
    }

    public void setChannelPort(Integer channelPort) {
        this.channelPort = channelPort;
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

    public String getDistinguishType() {
        return distinguishType;
    }

    public void setDistinguishType(String distinguishType) {
        this.distinguishType = distinguishType == null ? null : distinguishType.trim();
    }

    public String getSipIp() {
        return sipIp;
    }

    public void setSipIp(String sipIp) {
        this.sipIp = sipIp == null ? null : sipIp.trim();
    }

    public String getNvrIp() {
        return nvrIp;
    }

    public void setNvrIp(String nvrIp) {
        this.nvrIp = nvrIp;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nvrId=").append(nvrId);
        sb.append(", sipId=").append(sipId);
        sb.append(", nvrNumber=").append(nvrNumber);
        sb.append(", channelNumber=").append(channelNumber);
        sb.append(", channelName=").append(channelName);
        sb.append(", channelAccount=").append(channelAccount);
        sb.append(", channelPassword=").append(channelPassword);
        sb.append(", manufacturer=").append(manufacturer);
        sb.append(", channelModel=").append(channelModel);
        sb.append(", channelIp=").append(channelIp);
        sb.append(", channelPort=").append(channelPort);
        sb.append(", resolutionRatio=").append(resolutionRatio);
        sb.append(", codeRate=").append(codeRate);
        sb.append(", distinguishType=").append(distinguishType);
        sb.append(", sipIp=").append(sipIp);
        sb.append(", nvrIp=").append(nvrIp);
        sb.append(", onlineStatus=").append(onlineStatus);
        sb.append(", status=").append(status);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}