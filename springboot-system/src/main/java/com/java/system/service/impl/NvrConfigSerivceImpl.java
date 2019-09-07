package com.java.system.service.impl;

import com.java.common.constants.ParamConstant;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.DateUtil;
import com.java.model.dao.ChannelConfigMapper;
import com.java.model.dao.DeviceConfigMapper;
import com.java.model.dao.NvrConfigMapper;
import com.java.model.domain.ChannelConfig;
import com.java.model.domain.DeviceConfig;
import com.java.model.domain.NvrConfig;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.NvrConfigService;
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
public class NvrConfigSerivceImpl implements NvrConfigService {


    @Autowired
    private NvrConfigMapper nvrConfigMapper;

    @Autowired
    private ChannelConfigMapper channelConfigMapper;

    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Override
    public RestResult save(NvrConfig nvrConfig) {
        
        Date currentDate = new Date();

        nvrConfig.setStatus(0);
        nvrConfig.setDelStatus(0);
        nvrConfig.setCreateTime(currentDate);
        nvrConfigMapper.insertSelective(nvrConfig);

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

        // NVR配置信息 逻辑删除
        nvrConfigMapper.deleteBatch(ids);

        for (int i = 0; i < ids.length; i++){
            // 删除对应的通道和设备
            List<ChannelConfig> ccList = channelConfigMapper.findByNvrId(ids[i]);
            Integer[] ccIds = new Integer[ccList.size()];
            Integer[] dcIds = new Integer[ccList.size()];
            for (int j = 0; j < ccList.size(); j++){
                ccIds[j] = ccList.get(j).getId();
                dcIds[j] = ccList.get(j).getDeviceId();
            }
            channelConfigMapper.deleteBatch(ccIds);
            deviceConfigMapper.deleteBatch(dcIds);
        }

        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult update(NvrConfig nvrConfig) {
        nvrConfig.setUpdateTime(DateUtil.getCurrentDate());
        nvrConfigMapper.updateByPrimaryKeySelective(nvrConfig);
        List<ChannelConfig> ccList = channelConfigMapper.findByNvrId(nvrConfig.getId());
        for (int i = 0; i < ccList.size(); i++){
            ChannelConfig cc = ccList.get(i);
            cc.setNvrNumber(nvrConfig.getNvrNumber());
            cc.setUpdateTime(DateUtil.getCurrentDate());
            channelConfigMapper.updateByPrimaryKeySelective(cc);
        }

        return ResultUtils.success("更新成功");
    }

    @Override
    public RestResult getNvrConfig(Integer serverId) {

        return ResultUtils.success(nvrConfigMapper.selectByPrimaryKey(serverId));
    }

    @Override
    public RestResult setStatus(Integer pkId){
        NvrConfig nvrConfig = nvrConfigMapper.selectByPrimaryKey(pkId);
        if(nvrConfig.getStatus() == 0){// 设置禁用
            nvrConfig.setStatus(1);
        }else{ //设置启用
            nvrConfig.setStatus(0);
        }
        // 禁用NVR设备
        nvrConfigMapper.updateByPrimaryKeySelective(nvrConfig);

        // 禁用对应的通道和设备
        List<ChannelConfig> ccList = channelConfigMapper.findByNvrId(pkId);
        for (int i = 0; i < ccList.size(); i++){
            ChannelConfig cc = ccList.get(i);
            cc.setStatus(nvrConfig.getStatus());
            channelConfigMapper.updateByPrimaryKeySelective(cc);

            DeviceConfig dc = deviceConfigMapper.selectByPrimaryKey(cc.getDeviceId());
            dc.setStatus(cc.getStatus());
            deviceConfigMapper.updateByPrimaryKey(dc);
        }

        return ResultUtils.success(nvrConfig);
    }

    @Override
    public RestResult listPage(String keyword, Integer userId, Integer pageNum, Integer pageSize) {

        int n = pageNum == 1 ? pageNum = 0 : (pageNum = (pageNum - 1) * pageSize);

        // 查询参数
        Map domainMap = new HashMap();
        domainMap.put(ParamConstant.KEY_WORD,keyword);
        domainMap.put("userId",userId);
        domainMap.put(ParamConstant.PAGE_SIZE,pageSize);
        domainMap.put(ParamConstant.PAGE_NUM,pageNum);

        List<Map> serverConfigs = nvrConfigMapper.listPage(domainMap);
        for (Map map: serverConfigs){
            List<ChannelConfig> ccList = channelConfigMapper.findByNvrId((Integer) map.get("id"));
            map.put("nvrChannelNumStr", ccList.size() + "/" + map.get("nvrChannelNum"));
        }
        int count = nvrConfigMapper.countPage(domainMap);

        return ResultUtils.success(new PageResult<Map>(serverConfigs,count));
    }

    @Override
    public RestResult findByUserId(Integer userId){
        List<NvrConfig> cnList = nvrConfigMapper.findByUserId(userId);
        for (NvrConfig cn: cnList){
            List<ChannelConfig> ccList = channelConfigMapper.findByNvrId(cn.getId());
            cn.setNvrChannelNumStr(ccList.size() + "/" + cn.getNvrChannelNum());
        }
        return ResultUtils.success(cnList);
    }

}
