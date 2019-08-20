package com.java.system.service.impl;

import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.DateUtil;
import com.java.model.dao.SysFileMapper;
import com.java.model.domain.SysFile;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class SysFileServiceImpl implements SysFileService {


    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;

    @Override
    public RestResult saveSysFile(SysFile sysFile) {

        Date date = DateUtil.getCurrentDate();
        sysFile.setCreateTime(date);

        sysFileMapper.insertSelective(sysFile);

        if (null == sysFile.getId()){
            throw new IllegalArgumentException();
        }
        return ResultUtils.success("保存成功");
    }

    @Override
    public RestResult deleteFile(Integer filePid, String dataType) {

        // 管理员信息 逻辑删除
        sysFileMapper.deleteBatch(filePid, dataType);
        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult listFile(Integer filePid, String dataType) {

        // 查询参数
        Map domainMap = new HashMap();
        domainMap.put("filePid",filePid);
        domainMap.put("dataType",dataType);

        List<SysFile> admins = sysFileMapper.listFile(domainMap);

        return ResultUtils.success(admins);
    }
}
