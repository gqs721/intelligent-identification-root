package com.java.system.service.impl;

import com.java.common.enums.ResultCodeEnum;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.DateUtil;
import com.java.model.dao.DeviceGroupMapper;
import com.java.model.domain.DeviceGroup;
import com.java.system.service.DeviceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class DeviceGroupServiceImpl implements DeviceGroupService {


    @Autowired
    private DeviceGroupMapper deviceGroupMapper;

    @Override
    public RestResult save(DeviceGroup deviceConfig) {
        deviceConfig.setCreateTime(DateUtil.getCurrentDate());
        deviceGroupMapper.insertSelective(deviceConfig);
        return ResultUtils.success("添加成功");

    }

    @Override
    public RestResult delete(Integer pkId) {
        deviceGroupMapper.deleteByPrimaryKey(pkId);
        // TODO 对应的设备也得修改

        return ResultUtils.success("删除成功");

    }

    @Override
    public RestResult update(DeviceGroup deviceConfig) {
        deviceGroupMapper.updateByPrimaryKeySelective(deviceConfig);
        return ResultUtils.success("修改成功");

    }

    @Override
    public RestResult getAllByUserId(Integer userId) {

        if (null == userId) {
            return ResultUtils.error(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(), ResultCodeEnum.ILLEGAL_ARGUMENT.getMsg());
        }

        List<DeviceGroup> resourceList = deviceGroupMapper.findByUserId(userId);

//        JSONArray treeList = new JSONArray();
//        List<DeviceGroup> nodeList = new ArrayList<>();
//
//        // 遍历以确定分组上下级关系
//        for (DeviceGroup r : resourceList) {
//            if (r.getParent() != null) {
//                r.setParent(r.getParent());
//            }
//            nodeList.add(r);
//        }
//
//        // 再一次遍历将group属性复制到JSONObject中，为了在前台能够显示树形结构
//        for (DeviceGroup group : nodeList) {
//            JSONObject node = new JSONObject();
//
//            if (group.getParent() != null) {
//                group.setParent(group.getParent());
//                node.put("parentId", group.getParent().getId());
//                node.put("parentName", group.getParent().getGroupName());// 设置上级分组名
//            } else {
//                node.put("parentId", 0);
//                node.put("parentName", "");// 设置上级分组名
//            }
//            node.put("id", group.getId());// 设置节点Id
//            node.put("userId", group.getUserId());
//            node.put("groupName", group.getGroupName());// 设置节点名称
//            treeList.add(node);
//        }

        return ResultUtils.success(resourceList);
    }

}
