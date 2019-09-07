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
import com.java.model.domain.AlarmRecord;
import com.java.model.domain.DictData;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.AlarmRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class AlarmRecordSerivceImpl implements AlarmRecordService {


    @Autowired
    private AlarmRecordMapper alarmRecordMapper;
    @Autowired
    private DictTypeMapper dictTypeMapper;
    @Autowired
    private DictDataMapper dictDataMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Override
    public RestResult save(AlarmRecord alarmRecord) {
        
        Date currentDate = new Date();

        alarmRecord.setAlarmDate(currentDate);
        alarmRecord.setAlarmTime(DateUtil.getTime());
        alarmRecord.setStatus(0);
        alarmRecord.setDelStatus(0);
        alarmRecord.setCreateTime(currentDate);
        alarmRecordMapper.insertSelective(alarmRecord);

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
        alarmRecordMapper.deleteBatch(ids);
        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult listPage(Integer alarmType, Integer deviceId, Integer userId, String beginDate, String endDate, Integer pageNum, Integer pageSize) {

        int n = pageNum == 1 ? pageNum = 0 : (pageNum = (pageNum - 1) * pageSize);

        // 查询参数
        Map domainMap = new HashMap();
        domainMap.put("alarmType",alarmType);
        domainMap.put("deviceId",deviceId);
        domainMap.put("userId",userId);
//        if (!StringUtil.isEmpty(beginDate)) {
            domainMap.put("beginDate", beginDate);
//        }else{
//            domainMap.put("beginDate", null);
//        }
//        if(!StringUtil.isEmpty(endDate)) {
            domainMap.put("endDate", endDate);
//        }else{
//            domainMap.put("endDate", null);
//        }
        domainMap.put(ParamConstant.PAGE_SIZE,pageSize);
        domainMap.put(ParamConstant.PAGE_NUM,pageNum);


        List<Map> alarmRecords = alarmRecordMapper.listPage(domainMap);
        int count = alarmRecordMapper.countPage(domainMap);

        return ResultUtils.success(new PageResult<Map>(alarmRecords,count));
    }

    @Override
    public RestResult alarmTypeStatistical(Integer day){
        List<DictData> ddList = dictDataMapper.findByTypeCode("alarm_type");
        List<Map> mapList = new ArrayList<>();
        for(int i = 0; i<ddList.size(); i++){
            Map map = new HashMap();
            map.put("dictCode", Integer.parseInt(ddList.get(i).getDictCode()));
            map.put("dictValue", ddList.get(i).getDictValue());
            List<Map> maps = alarmRecordMapper.alarmTypeStatistical(Integer.parseInt(ddList.get(i).getDictCode()),day);
            map.put("list",maps);
            mapList.add(map);
        }
//        List<Map> maps = alarmRecordMapper.alarmTypeStatistical(1,day);
        return ResultUtils.success(mapList);
    }

    @Override
    public RestResult nowAlarmTypeStatistical(){
        List<Map> maps = alarmRecordMapper.nowAlarmTypeStatistical();

        List<DictData> ddList = dictDataMapper.findByTypeCode("alarm_type");
        for(int i = 0; i<maps.size(); i++){
            for(int j = 0; j<ddList.size(); j++){
                if(StringUtil.CheckIsEqual(maps.get(i).get("alarmType").toString(), ddList.get(j).getDictCode())){
                    maps.get(i).put("alarmName", ddList.get(j).getDictValue());
                    break;
                }else{
                    maps.get(i).put("alarmName", "未知类型");
                }
            }
        }

        Integer allAlarmTypeCount = alarmRecordMapper.nowAlarmTypeCount();

        Map map = new HashMap();
        map.put("count",allAlarmTypeCount);
        map.put("list", maps);
        return ResultUtils.success(map);
    }

}
