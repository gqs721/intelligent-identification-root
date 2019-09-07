package com.java.system.service.impl;

import com.java.common.constants.ParamConstant;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.StringUtil;
import com.java.model.dao.*;
import com.java.model.domain.DictData;
import com.java.model.domain.ServerConfig;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.ServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class ServerConfigSerivceImpl implements ServerConfigService {


    @Autowired
    private ServerConfigMapper serverConfigMapper;

    @Autowired
    private DictDataMapper dictDataMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Override
    public RestResult save(ServerConfig serverConfig) {
        
        Date currentDate = new Date();

        serverConfig.setStatus(0);
        serverConfig.setDelStatus(0);
        serverConfig.setCreateTime(currentDate);
        serverConfigMapper.insertSelective(serverConfig);

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
        serverConfigMapper.deleteBatch(ids);
        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult update(ServerConfig serverConfig) {

        serverConfigMapper.updateByPrimaryKeySelective(serverConfig);

        return ResultUtils.success("更新成功");
    }

    @Override
    public RestResult getServerConfig(Integer serverId) {

        return ResultUtils.success(serverConfigMapper.selectByPrimaryKey(serverId));
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

        List<Map> serverConfigs = serverConfigMapper.listPage(domainMap);
        int count = serverConfigMapper.countPage(domainMap);

        return ResultUtils.success(new PageResult<Map>(serverConfigs,count));
    }

    @Override
    public RestResult findByUserId(Integer userId){
        List<ServerConfig> scList = serverConfigMapper.findByUserId(userId);
        List<DictData> ddList = dictDataMapper.findByTypeCode("identification_type");
        for (ServerConfig sc : scList){
            for (DictData dd : ddList){
                if(StringUtil.CheckIsEqual(sc.getIdentificationType(), dd.getDictCode())){
                    sc.setIdentificationTypeStr(dd.getDictValue());
                }
            }
        }
        return ResultUtils.success(scList);
    }

}
