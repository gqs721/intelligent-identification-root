package com.java.system.service.impl;

import com.java.common.constants.ParamConstant;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.model.dao.DeviceConfigMapper;
import com.java.model.dao.StreamMediaConfigMapper;
import com.java.model.domain.DeviceConfig;
import com.java.model.domain.StreamMediaConfig;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.StreamMediaConfigService;
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
public class StreamMediaConfigSerivceImpl implements StreamMediaConfigService {


    @Autowired
    private StreamMediaConfigMapper streamMediaConfigMapper;

    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Override
    public RestResult save(StreamMediaConfig streamMediaConfig) {
        
        Date currentDate = new Date();

        streamMediaConfig.setStatus(0);
        streamMediaConfig.setDelStatus(0);
        streamMediaConfig.setCreateTime(currentDate);
        streamMediaConfigMapper.insertSelective(streamMediaConfig);

        return ResultUtils.success("保存成功");
    }

    @Override
    public RestResult delete(List<Integer> pkIds) {
        if(pkIds == null || pkIds.isEmpty()){
            return ResultUtils.error(1,"没有选择需要删除的数据");
        }

        Integer[] ids = new Integer[pkIds.size()];

        if (pkIds.size() > 0){
            for (int i = 0; i < pkIds.size(); i++) {
                ids[i] = pkIds.get(i);
            }
        }

        // 管理员信息 逻辑删除
        streamMediaConfigMapper.deleteBatch(ids);
        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult update(StreamMediaConfig streamMediaConfig) {

        streamMediaConfigMapper.updateByPrimaryKeySelective(streamMediaConfig);

        return ResultUtils.success("更新成功");
    }

    @Override
    public RestResult getStreamMediaConfig(Integer serverId) {

        return ResultUtils.success(streamMediaConfigMapper.selectByPrimaryKey(serverId));
    }

    @Override
    public RestResult listPage(String keyword, Integer pageNum, Integer pageSize) {

        int n = pageNum == 1 ? pageNum = 0 : (pageNum = (pageNum - 1) * pageSize);

        // 查询参数
        Map domainMap = new HashMap();
        domainMap.put(ParamConstant.KEY_WORD,keyword);
        domainMap.put(ParamConstant.PAGE_SIZE,pageSize);
        domainMap.put(ParamConstant.PAGE_NUM,pageNum);

        List<Map> serverConfigs = streamMediaConfigMapper.listPage(domainMap);
        int count = streamMediaConfigMapper.countPage(domainMap);

        return ResultUtils.success(new PageResult<Map>(serverConfigs,count));
    }

    @Override
    public RestResult findByUserId(Integer userId){
        List<StreamMediaConfig> smcList = streamMediaConfigMapper.findByUserId(userId);
        for (StreamMediaConfig smc : smcList){
            List<DeviceConfig> dcList = deviceConfigMapper.findBySipId(smc.getId());
            smc.setAccessRouteStr(dcList.size() + "/" + smc.getAccessRoute());
        }
        return ResultUtils.success(smcList);
    }

}
