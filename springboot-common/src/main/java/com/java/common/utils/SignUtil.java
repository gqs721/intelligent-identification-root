package com.java.common.utils;


import com.java.model.domain.Admin;

import java.util.*;

/**
 * Created by Mr.BH
 */
public class SignUtil {


    public static void main(String[] args) {

        Map map = new HashMap();
        map.put("token",";klsdjfl;aknfadfja;skdfj");
        map.put("account","17712078858");
        map.put("pageNum","1");
        map.put("pageSize","10");
        map.put("keyword","dxs");
        map.put("version","1.1.0");
        map.put("timestamp","123123019283");
        Admin admin = new Admin();
        admin.setId(1);
        admin.setRealName("sdfsf");
        admin.setUserName("sdd");
        map.put("user",admin);

        getSign(map);
    }


    public static String createSign(String key, SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        String sign = MD5Util.MD5Encode(sb.toString()).toUpperCase();
        return sign;
    }


    /**
     * 生成签名
     * @param map
     * @return
     */
    public static String getSign(Map<String, String> map) {

        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

//            JSONObject jsonObject = JSONObject.fromObject(infoIds);

            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + ":" + val + ":");
                    }
                }
            }
//			sb.append(PropertyManager.getProperty("SIGNKEY"));
            result = sb.toString();

            //进行MD5加密
//            result = DigestUtils.md5Hex(result).toUpperCase();
        } catch (Exception e) {
            return null;
        }
        return result;
    }



}
