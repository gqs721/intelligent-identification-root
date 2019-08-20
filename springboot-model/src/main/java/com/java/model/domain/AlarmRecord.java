package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AlarmRecord {
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
     * 设备id
     */
    @ApiModelProperty(value="设备id")
    private Integer deviceId;

    /**
     * 设备名
     */
    @ApiModelProperty(value="设备名")
    private String deviceName;

    /**
     * 识别人员id
     */
    @ApiModelProperty(value="识别人员id")
    private Integer peopleId;

    /**
     * 人员类型id
     */
    @ApiModelProperty(value="人员类型id")
    private Integer peopleKind;

    /**
     * 人员姓名
     */
    @ApiModelProperty(value="人员姓名")
    private String peopleName;

    /**
     * 截图
     */
    @ApiModelProperty(value="截图")
    private String printscreen;

    /**
     * imageFile
     */
    @ApiModelProperty(value="imageFile")
    private String imageFile;

    /**
     * smallImage
     */
    @ApiModelProperty(value="smallImage")
    private String smallImage;

    /**
     * smallUrl
     */
    @ApiModelProperty(value="smallUrl")
    private String smallUrl;

    /**
     * 告警类型
     */
    @ApiModelProperty(value="告警类型")
    private Integer alarmType;

    /**
     * 告警日期
     */
    @ApiModelProperty(value="告警日期")
    private Date alarmDate;

    private String alarmDateStr;

    /**
     * 告警时间
     */
    @ApiModelProperty(value="告警时间")
    private String alarmTime;

    /**
     * 状态
     */
    @ApiModelProperty(value="状态")
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

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName == null ? null : peopleName.trim();
    }

    public String getPrintscreen() {
        return printscreen;
    }

    public void setPrintscreen(String printscreen) {
        this.printscreen = printscreen == null ? null : printscreen.trim();
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmDateStr() {
        return alarmDateStr;
    }

    public void setAlarmDateStr(String alarmDateStr) {
        this.alarmDateStr = alarmDateStr;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime == null ? null : alarmTime.trim();
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

    public Integer getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Integer peopleId) {
        this.peopleId = peopleId;
    }

    public Integer getPeopleKind() {
        return peopleKind;
    }

    public void setPeopleKind(Integer peopleKind) {
        this.peopleKind = peopleKind;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", peopleName=").append(peopleName);
        sb.append(", printscreen=").append(printscreen);
        sb.append(", alarmType=").append(alarmType);
        sb.append(", alarmDate=").append(alarmDate);
        sb.append(", alarmTime=").append(alarmTime);
        sb.append(", status=").append(status);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}