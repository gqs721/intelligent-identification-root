package com.java.common.utils;

import com.java.common.constants.ParamConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.BH
 */
public class PageUtils {

    /**
     * 处理、分页请求参数
     * @param map
     * @return
     */
    public static Map formatPageParam(Map map){
        Map queryMap = new HashMap();

        String keyword = (String) map.get(ParamConstant.KEY_WORD);
        Integer pageNum = (Integer) map.get(ParamConstant.PAGE_NUM);
        Integer pageSize = (Integer) map.get(ParamConstant.PAGE_SIZE);
        Integer type = (Integer) map.get("type");

        if (!StringUtil.isEmpty(keyword)){
            queryMap.put(ParamConstant.KEY_WORD,keyword);
        }
        if (pageNum < 0){
            pageNum = 0;
        }
        if (pageSize <= 0){
            pageSize = 10;
        }
        // pageNum 为 1，返回数据集从第二条开始
        int n = pageNum == 1 ? pageNum = 0 : (pageNum = (pageNum-1)*pageSize);
        queryMap.put(ParamConstant.PAGE_NUM,pageNum);
        queryMap.put(ParamConstant.PAGE_SIZE,pageSize);

        if (null != type && type >= 0 ){
            queryMap.put("type",type);
        }

        return queryMap;
    }
}
