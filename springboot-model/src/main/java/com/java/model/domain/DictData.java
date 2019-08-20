package com.java.model.domain;

import io.swagger.annotations.ApiModelProperty;

public class DictData {
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 所属字典类型
     */
    @ApiModelProperty(value="所属字典类型")
    private Integer dictTypeId;

    /**
     * 字典编码
     */
    @ApiModelProperty(value="字典编码")
    private String dictCode;

    /**
     * 字典值
     */
    @ApiModelProperty(value="字典值")
    private String dictValue;

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

    public Integer getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Integer dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
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
        sb.append(", dictTypeId=").append(dictTypeId);
        sb.append(", dictCode=").append(dictCode);
        sb.append(", dictValue=").append(dictValue);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}