package com.java.model.dao;

import com.java.model.domain.SysFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    SysFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);

    void deleteBatch(@Param("filePid") Integer filePid, @Param("dataType") String dataType);

    List<SysFile> listFile(Map domainMap);
}