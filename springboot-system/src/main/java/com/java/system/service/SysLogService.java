package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.SysLog;

/**
 * Created by Mr.BH
 */
public interface SysLogService {

    /**
     * 添加操作日志
     * @param sysLog
     * @return
     */
    RestResult save(SysLog sysLog);
}
