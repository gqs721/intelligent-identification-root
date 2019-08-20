package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ServerConfig {
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
     * 服务器编号
     */
    @ApiModelProperty(value="服务器编号")
    private String serverNumber;

    /**
     * 服务器ip
     */
    @ApiModelProperty(value="服务器ip")
    private String serverIp;

    /**
     * 服务器端口
     */
    @ApiModelProperty(value="服务器端口")
    private Integer serverPort;

    /**
     * 账号
     */
    @ApiModelProperty(value="账号")
    private String serverAccount;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String serverPassword;

    /**
     * 识别类型
     */
    @ApiModelProperty(value="识别类型")
    private String identificationType;

    /**
     * 识别路数
     */
    @ApiModelProperty(value="识别路数")
    private String identifyTheImlet;

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

    public String getServerNumber() {
        return serverNumber;
    }

    public void setServerNumber(String serverNumber) {
        this.serverNumber = serverNumber == null ? null : serverNumber.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerAccount() {
        return serverAccount;
    }

    public void setServerAccount(String serverAccount) {
        this.serverAccount = serverAccount == null ? null : serverAccount.trim();
    }

    public String getServerPassword() {
        return serverPassword;
    }

    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword == null ? null : serverPassword.trim();
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType == null ? null : identificationType.trim();
    }

    public String getIdentifyTheImlet() {
        return identifyTheImlet;
    }

    public void setIdentifyTheImlet(String identifyTheImlet) {
        this.identifyTheImlet = identifyTheImlet == null ? null : identifyTheImlet.trim();
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
        sb.append(", serverNumber=").append(serverNumber);
        sb.append(", serverIp=").append(serverIp);
        sb.append(", serverAccount=").append(serverAccount);
        sb.append(", serverPassword=").append(serverPassword);
        sb.append(", identificationType=").append(identificationType);
        sb.append(", identifyTheImlet=").append(identifyTheImlet);
        sb.append(", createUserType=").append(createUserType);
        sb.append(", status=").append(status);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}