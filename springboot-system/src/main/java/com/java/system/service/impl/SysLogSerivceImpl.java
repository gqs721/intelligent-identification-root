package com.java.system.service.impl;

import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.model.dao.*;
import com.java.model.domain.SysLog;
import com.java.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class SysLogSerivceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;


    @Override
    public RestResult save(SysLog sysLog) {
        sysLogMapper.insertSelective(sysLog);
        return ResultUtils.success(null);
    }

}
