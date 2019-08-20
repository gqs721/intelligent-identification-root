package com.java.model.domain;

import java.util.Date;

public class IdentificationLibrary {
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
     * 服务器id
     */
    @ApiModelProperty(value="服务器id")
    private Integer serverId;

    /**
     * 流媒体id
     */
    @ApiModelProperty(value="流媒体id")
    private Integer sipId;

    /**
     * 设备名称
     */
    @ApiModelProperty(value="设备名称")
    private String deviceName;

    /**
     * 识别类型
     */
    @ApiModelProperty(value="识别类型")
    private String identificationType;

    /**
     * 识别人员id
     */
    @ApiModelProperty(value="识别人员id")
    private Integer peopleId;

    /**
     * 人员类型
     */
    @ApiModelProperty(value="人员类型")
    private Integer peopleKind;

    /**
     * 人员姓名
     */
    @ApiModelProperty(value="人员姓名")
    private String peopleName;

    /**
     * image_url
     */
    @ApiModelProperty(value="image_url")
    private String imageUrl;

    /**
     * image_file
     */
    @ApiModelProperty(value="image_file")
    private String imageFile;

    /**
     * small_image
     */
    @ApiModelProperty(value="small_image")
    private String smallImage;

    /**
     * small_url
     */
    @ApiModelProperty(value="small_url")
    private String smallUrl;

    /**
     * 识别时间
     */
    @ApiModelProperty(value="识别时间")
    private Date identificationTime;

    /**
     * 状态
     */
    @ApiModelProperty(value="状态")
    private Integer status;

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

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getSipId() {
        return sipId;
    }

    public void setSipId(Integer sipId) {
        this.sipId = sipId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType == null ? null : identificationType.trim();
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

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName == null ? null : peopleName.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile == null ? null : imageFile.trim();
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage == null ? null : smallImage.trim();
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl == null ? null : smallUrl.trim();
    }

    public Date getIdentificationTime() {
        return identificationTime;
    }

    public void setIdentificationTime(Date identificationTime) {
        this.identificationTime = identificationTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", serverId=").append(serverId);
        sb.append(", sipId=").append(sipId);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", identificationType=").append(identificationType);
        sb.append(", peopleId=").append(peopleId);
        sb.append(", peopleKind=").append(peopleKind);
        sb.append(", peopleName=").append(peopleName);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", imageFile=").append(imageFile);
        sb.append(", smallImage=").append(smallImage);
        sb.append(", smallUrl=").append(smallUrl);
        sb.append(", identificationTime=").append(identificationTime);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}