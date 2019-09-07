package com.java.system.service.impl;

import com.java.common.constants.ParamConstant;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.DateUtil;
import com.java.model.dao.OfficiaAccountsConfigMapper;
import com.java.model.domain.OfficiaAccountsConfig;
import com.java.model.domain.PushWeixin;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.OfficiaAccountsConfigService;
import com.java.system.service.PushWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class OfficiaAccountsConfigSerivceImpl implements OfficiaAccountsConfigService {


    @Autowired
    private OfficiaAccountsConfigMapper officiaAccountsConfigMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Override
    public RestResult save(OfficiaAccountsConfig officiaAccountsConfig) {
        
        Date currentDate = new Date();

        officiaAccountsConfig.setCreateTime(currentDate);
        officiaAccountsConfigMapper.insertSelective(officiaAccountsConfig);

        return ResultUtils.success("保存成功");
    }

    @Override
    public RestResult update(OfficiaAccountsConfig officiaAccountsConfig) {
        officiaAccountsConfig.setUpdateTime(DateUtil.getCurrentDate());
        officiaAccountsConfigMapper.updateByPrimaryKeySelective(officiaAccountsConfig);

        return ResultUtils.success("更新成功");
    }

    @Override
    public RestResult selectByUserId(Integer userId) {

        return ResultUtils.success(officiaAccountsConfigMapper.selectByUserId(userId));
    }

}
