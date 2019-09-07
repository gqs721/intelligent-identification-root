package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class OfficiaAccountsConfig {
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
     * 公众号名称
     */
    @ApiModelProperty(value="公众号名称")
    private String officiaAccountsName;

    /**
     * APPID
     */
    @ApiModelProperty(value="APPID")
    private String appId;

    /**
     * APP密钥
     */
    @ApiModelProperty(value="APP密钥")
    private String appSecret;

    /**
     * 告警模板id
     */
    @ApiModelProperty(value="告警模板id")
    private String alarmTemplateId;

    /**
     * 审核模板id
     */
    @ApiModelProperty(value="审核模板id")
    private String auditTemplateId;

    /**
     * 备用模板id1
     */
    @ApiModelProperty(value="备用模板id1")
    private String standbyTemplateId1;

    /**
     * 备用模板id2
     */
    @ApiModelProperty(value="备用模板id2")
    private String standbyTemplateId2;

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

    public String getOfficiaAccountsName() {
        return officiaAccountsName;
    }

    public void setOfficiaAccountsName(String officiaAccountsName) {
        this.officiaAccountsName = officiaAccountsName == null ? null : officiaAccountsName.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    public String getAlarmTemplateId() {
        return alarmTemplateId;
    }

    public void setAlarmTemplateId(String alarmTemplateId) {
        this.alarmTemplateId = alarmTemplateId == null ? null : alarmTemplateId.trim();
    }

    public String getAuditTemplateId() {
        return auditTemplateId;
    }

    public void setAuditTemplateId(String auditTemplateId) {
        this.auditTemplateId = auditTemplateId == null ? null : auditTemplateId.trim();
    }

    public String getStandbyTemplateId1() {
        return standbyTemplateId1;
    }

    public void setStandbyTemplateId1(String standbyTemplateId1) {
        this.standbyTemplateId1 = standbyTemplateId1 == null ? null : standbyTemplateId1.trim();
    }

    public String getStandbyTemplateId2() {
        return standbyTemplateId2;
    }

    public void setStandbyTemplateId2(String standbyTemplateId2) {
        this.standbyTemplateId2 = standbyTemplateId2 == null ? null : standbyTemplateId2.trim();
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
        sb.append(", officiaAccountsName=").append(officiaAccountsName);
        sb.append(", appId=").append(appId);
        sb.append(", appSecret=").append(appSecret);
        sb.append(", alarmTemplateId=").append(alarmTemplateId);
        sb.append(", auditTemplateId=").append(auditTemplateId);
        sb.append(", standbyTemplateId1=").append(standbyTemplateId1);
        sb.append(", standbyTemplateId2=").append(standbyTemplateId2);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}