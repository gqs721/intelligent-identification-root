package com.java.system.service.impl;

import com.java.common.constants.ParamConstant;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.DateUtil;
import com.java.model.dao.PushWeixinMapper;
import com.java.model.domain.PushWeixin;
import com.java.system.redis.JWTRedisDAO;
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
public class PushWeixinSerivceImpl implements PushWeixinService {


    @Autowired
    private PushWeixinMapper pushWeixinMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Override
    public RestResult save(PushWeixin pushWeixin) {
        
        Date currentDate = new Date();

        pushWeixin.setAuditStatus(0);
        pushWeixin.setIsAdmin(0);
        pushWeixin.setStatus(0);
        pushWeixin.setDelStatus(0);
        pushWeixin.setCreateTime(currentDate);
        pushWeixinMapper.insertSelective(pushWeixin);

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

        pushWeixinMapper.deleteBatch(ids);

        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult deleteByOpenId(String openId) {

        pushWeixinMapper.deleteByOpenId(openId);

        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult update(PushWeixin pushWeixin) {
        pushWeixin.setUpdateTime(DateUtil.getCurrentDate());
        pushWeixinMapper.updateByPrimaryKeySelective(pushWeixin);

        return ResultUtils.success("更新成功");
    }

    @Override
    public RestResult selectByOpenId(String openId) {

        return ResultUtils.success(pushWeixinMapper.selectByOpenId(openId));
    }

    @Override
    public RestResult setStatus(Integer pkId){
        PushWeixin pushWeixin = pushWeixinMapper.selectByPrimaryKey(pkId);
        if(pushWeixin.getStatus() == 0){// 设置禁用
            pushWeixin.setStatus(1);
        }else{ //设置启用
            pushWeixin.setStatus(0);
        }
        pushWeixinMapper.updateByPrimaryKeySelective(pushWeixin);

        return ResultUtils.success(pushWeixin);
    }

    @Override
    public RestResult setAdminStatus(Integer pkId, Integer adminStatus){
        PushWeixin pushWeixin = pushWeixinMapper.selectByPrimaryKey(pkId);

        pushWeixin.setIsAdmin(adminStatus);
        pushWeixinMapper.updateByPrimaryKeySelective(pushWeixin);

        return ResultUtils.success(pushWeixin);
    }

    @Override
    public RestResult setAuditStatus(Integer pkId, Integer auditStatus){
        PushWeixin pushWeixin = pushWeixinMapper.selectByPrimaryKey(pkId);

        pushWeixin.setAuditStatus(auditStatus);
        pushWeixinMapper.updateByPrimaryKeySelective(pushWeixin);

        return ResultUtils.success(pushWeixin);
    }

    @Override
    public RestResult wxSetStatus(Integer pkId){
        PushWeixin pushWeixin = pushWeixinMapper.selectByPrimaryKey(pkId);

        if(pushWeixin.getStatus() == 0){// 设置禁用
            pushWeixin.setStatus(2);
        }else if(pushWeixin.getStatus() == 2){ //设置启用
            pushWeixin.setStatus(0);
        }else{
            return ResultUtils.error(1,"你已被管理员禁用，请联系管理员处理");
        }

        pushWeixinMapper.updateByPrimaryKeySelective(pushWeixin);

        return ResultUtils.success(pushWeixin);
    }

    @Override
    public RestResult listPage(String keyword, Integer auditStatus, Integer userId, Integer pageNum, Integer pageSize) {

        int n = pageNum == 1 ? pageNum = 0 : (pageNum = (pageNum - 1) * pageSize);

        // 查询参数
        Map domainMap = new HashMap();
        domainMap.put(ParamConstant.KEY_WORD,keyword);
        domainMap.put("userId",userId);
        domainMap.put("auditStatus",auditStatus);
        domainMap.put(ParamConstant.PAGE_SIZE,pageSize);
        domainMap.put(ParamConstant.PAGE_NUM,pageNum);

        List<Map> serverConfigs = pushWeixinMapper.listPage(domainMap);
        int count = pushWeixinMapper.countPage(domainMap);

        return ResultUtils.success(new PageResult<Map>(serverConfigs,count));
    }

}
