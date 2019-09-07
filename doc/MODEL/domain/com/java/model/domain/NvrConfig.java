package com.java.model.domain;

import java.util.Date;

public class NvrConfig {
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
     * 公司名称
     */
    @ApiModelProperty(value="公司名称")
    private String companyName;

    /**
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String nvrNumber;

    /**
     * nvr名称
     */
    @ApiModelProperty(value="nvr名称")
    private String nvrName;

    /**
     * 厂商
     */
    @ApiModelProperty(value="厂商")
    private String manufacturer;

    /**
     * nvr账号
     */
    @ApiModelProperty(value="nvr账号")
    private String nvrAccount;

    /**
     * nvr密码
     */
    @ApiModelProperty(value="nvr密码")
    private String nvrPassword;

    /**
     * 型号
     */
    @ApiModelProperty(value="型号")
    private String nvrModel;

    /**
     * nvrip
     */
    @ApiModelProperty(value="nvrip")
    private String nvrIp;

    /**
     * nvr端口
     */
    @ApiModelProperty(value="nvr端口")
    private Integer nvrPort;

    /**
     * 通道数
     */
    @ApiModelProperty(value="通道数")
    private Integer nvrChannelNum;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getNvrNumber() {
        return nvrNumber;
    }

    public void setNvrNumber(String nvrNumber) {
        this.nvrNumber = nvrNumber == null ? null : nvrNumber.trim();
    }

    public String getNvrName() {
        return nvrName;
    }

    public void setNvrName(String nvrName) {
        this.nvrName = nvrName == null ? null : nvrName.trim();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    public String getNvrAccount() {
        return nvrAccount;
    }

    public void setNvrAccount(String nvrAccount) {
        this.nvrAccount = nvrAccount == null ? null : nvrAccount.trim();
    }

    public String getNvrPassword() {
        return nvrPassword;
    }

    public void setNvrPassword(String nvrPassword) {
        this.nvrPassword = nvrPassword == null ? null : nvrPassword.trim();
    }

    public String getNvrModel() {
        return nvrModel;
    }

    public void setNvrModel(String nvrModel) {
        this.nvrModel = nvrModel == null ? null : nvrModel.trim();
    }

    public String getNvrIp() {
        return nvrIp;
    }

    public void setNvrIp(String nvrIp) {
        this.nvrIp = nvrIp == null ? null : nvrIp.trim();
    }

    public Integer getNvrPort() {
        return nvrPort;
    }

    public void setNvrPort(Integer nvrPort) {
        this.nvrPort = nvrPort;
    }

    public Integer getNvrChannelNum() {
        return nvrChannelNum;
    }

    public void setNvrChannelNum(Integer nvrChannelNum) {
        this.nvrChannelNum = nvrChannelNum;
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
        sb.append(", userId=").append(userId);
        sb.append(", companyName=").append(companyName);
        sb.append(", nvrNumber=").append(nvrNumber);
        sb.append(", nvrName=").append(nvrName);
        sb.append(", manufacturer=").append(manufacturer);
        sb.append(", nvrAccount=").append(nvrAccount);
        sb.append(", nvrPassword=").append(nvrPassword);
        sb.append(", nvrModel=").append(nvrModel);
        sb.append(", nvrIp=").append(nvrIp);
        sb.append(", nvrPort=").append(nvrPort);
        sb.append(", nvrChannelNum=").append(nvrChannelNum);
        sb.append(", status=").append(status);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}