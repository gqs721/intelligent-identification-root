package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

public class DictType {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 类型编码
     */
    @ApiModelProperty(value="类型编码")
    private String typeCode;

    /**
     * 类型值
     */
    @ApiModelProperty(value="类型值")
    private String typeValue;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue == null ? null : typeValue.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", typeCode=").append(typeCode);
        sb.append(", typeValue=").append(typeValue);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}