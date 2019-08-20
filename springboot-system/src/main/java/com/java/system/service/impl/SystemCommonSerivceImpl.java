package com.java.system.service.impl;

import com.java.common.constants.ParamConstant;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.DateUtil;
import com.java.common.utils.StringUtil;
import com.java.model.dao.AlarmRecordMapper;
import com.java.model.dao.DictDataMapper;
import com.java.model.dao.DictTypeMapper;
import com.java.model.dao.IdentificationLibraryMapper;
import com.java.model.domain.AlarmRecord;
import com.java.model.domain.DictData;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.AlarmRecordService;
import com.java.system.service.SystemCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class SystemCommonSerivceImpl implements SystemCommonService {

    @Autowired
    private AlarmRecordMapper alarmRecordMapper;


    @Override
    public RestResult savePushWeixin(String openId) {
        if(StringUtil.isEmpty(openId)){
            return ResultUtils.error(1,"绑定失败");
        }
        List<String> openIdList = alarmRecordMapper.findPushWeixin();
        boolean flag = true;
        for (int i = 0; i < openIdList.size(); i++){
            if(StringUtil.CheckIsEqual(openIdList.get(i), openId)){
                flag = false;
                break;
            }
        }
        if(flag){
            Date currentDate = new Date();
            alarmRecordMapper.savePushWeixin(openId, currentDate);
            return ResultUtils.success("保存成功");
        }else{
            return ResultUtils.error(1,"该微信已绑定过");
        }
    }

    @Override
    public RestResult unbindOpenId(String openId) {
        return ResultUtils.success(alarmRecordMapper.unbindOpenId(openId));
    }

}
