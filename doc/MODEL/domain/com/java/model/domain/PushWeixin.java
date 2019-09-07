package com.java.model.domain;

import java.util.Date;

public class PushWeixin {
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
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像")
    private String wxPhoto;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String wxName;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String wxPhone;

    /**
     * openId
     */
    @ApiModelProperty(value="openId")
    private String openId;

    /**
     * 推送类型，用于列表显示
     */
    @ApiModelProperty(value="推送类型，用于列表显示")
    private String pushType;

    /**
     * 审核状态，0：待审核，1：审核通过，2：审核不通过
     */
    @ApiModelProperty(value="审核状态，0：待审核，1：审核通过，2：审核不通过")
    private Integer auditStatus;

    /**
     * 是否管理员，默认0
     */
    @ApiModelProperty(value="是否管理员，默认0")
    private Integer isAdmin;

    /**
     * 状态，0：启用，1：系统禁用，2：自己禁用
     */
    @ApiModelProperty(value="状态，0：启用，1：系统禁用，2：自己禁用")
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getWxPhoto() {
        return wxPhoto;
    }

    public void setWxPhoto(String wxPhoto) {
        this.wxPhoto = wxPhoto == null ? null : wxPhoto.trim();
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public String getWxPhone() {
        return wxPhone;
    }

    public void setWxPhone(String wxPhone) {
        this.wxPhone = wxPhone == null ? null : wxPhone.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType == null ? null : pushType.trim();
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
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
        sb.append(", nickName=").append(nickName);
        sb.append(", wxPhoto=").append(wxPhoto);
        sb.append(", wxName=").append(wxName);
        sb.append(", wxPhone=").append(wxPhone);
        sb.append(", openId=").append(openId);
        sb.append(", pushType=").append(pushType);
        sb.append(", auditStatus=").append(auditStatus);
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", status=").append(status);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}