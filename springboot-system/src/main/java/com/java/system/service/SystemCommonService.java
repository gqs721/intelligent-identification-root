package com.java.system.service;

import com.java.common.result.RestResult;

/**
 * Created by Mr.BH
 */
public interface SystemCommonService {


    RestResult savePushWeixin(String openId);

    RestResult unbindOpenId(String openId);

    RestResult findDictByTypeCode(String typeCode);
}
