package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

public class SysLog {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * 操作模块
     */
    @ApiModelProperty(value="操作模块")
    private String operation;

    /**
     * 方法
     */
    @ApiModelProperty(value="方法")
    private String method;

    /**
     * 操作ip
     */
    @ApiModelProperty(value="操作ip")
    private String ip;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private String createTime;

    /**
     * 参数
     */
    @ApiModelProperty(value="参数")
    private String params;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", operation=").append(operation);
        sb.append(", method=").append(method);
        sb.append(", ip=").append(ip);
        sb.append(", createTime=").append(createTime);
        sb.append(", params=").append(params);
        sb.append("]");
        return sb.toString();
    }
}