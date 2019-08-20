package com.java.model.domain;

import java.util.Date;

public class StreamMediaConfig {
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
     * sip服务器编号
     */
    @ApiModelProperty(value="sip服务器编号")
    private String sipServerNumber;

    /**
     * sip服务器ip
     */
    @ApiModelProperty(value="sip服务器ip")
    private String sipServerIp;

    /**
     * sip域
     */
    @ApiModelProperty(value="sip域")
    private String sipRegion;

    /**
     * sip端口
     */
    @ApiModelProperty(value="sip端口")
    private Integer sipPort;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 接入路数
     */
    @ApiModelProperty(value="接入路数")
    private String accessRoute;

    /**
     * 创建用户类型
     */
    @ApiModelProperty(value="创建用户类型")
    private Integer createUserType;

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

    public String getSipServerNumber() {
        return sipServerNumber;
    }

    public void setSipServerNumber(String sipServerNumber) {
        this.sipServerNumber = sipServerNumber == null ? null : sipServerNumber.trim();
    }

    public String getSipServerIp() {
        return sipServerIp;
    }

    public void setSipServerIp(String sipServerIp) {
        this.sipServerIp = sipServerIp == null ? null : sipServerIp.trim();
    }

    public String getSipRegion() {
        return sipRegion;
    }

    public void setSipRegion(String sipRegion) {
        this.sipRegion = sipRegion == null ? null : sipRegion.trim();
    }

    public Integer getSipPort() {
        return sipPort;
    }

    public void setSipPort(Integer sipPort) {
        this.sipPort = sipPort;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAccessRoute() {
        return accessRoute;
    }

    public void setAccessRoute(String accessRoute) {
        this.accessRoute = accessRoute == null ? null : accessRoute.trim();
    }

    public Integer getCreateUserType() {
        return createUserType;
    }

    public void setCreateUserType(Integer createUserType) {
        this.createUserType = createUserType;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", companyName=").append(companyName);
        sb.append(", sipServerNumber=").append(sipServerNumber);
        sb.append(", sipServerIp=").append(sipServerIp);
        sb.append(", sipRegion=").append(sipRegion);
        sb.append(", sipPort=").append(sipPort);
        sb.append(", password=").append(password);
        sb.append(", accessRoute=").append(accessRoute);
        sb.append(", createUserType=").append(createUserType);
        sb.append(", status=").append(status);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}