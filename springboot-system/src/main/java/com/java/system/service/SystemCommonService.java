package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.AlarmRecord;

import java.util.List;

/**
 * Created by Mr.BH
 */
public interface SystemCommonService {


    RestResult savePushWeixin(String openId);

    RestResult unbindOpenId(String openId);
}
