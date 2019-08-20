package com.java.system.service.impl;

import com.java.common.aop.WebLogAspect;
import com.java.common.constants.AdminConstant;
import com.java.common.constants.ParamConstant;
import com.java.common.enums.ResultCodeEnum;
import com.java.common.exceptions.APIRunTimeException;
import com.java.common.exceptions.RoleNotExistException;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.DateUtil;
import com.java.common.utils.EncryptUtils;
import com.java.common.utils.JWTUtils;
import com.java.common.utils.StringUtil;
import com.java.model.dao.AdminMapper;
import com.java.model.dao.AdminRoleMapper;
import com.java.model.dao.RoleMapper;
import com.java.model.domain.Admin;
import com.java.model.domain.AdminRole;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.*;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RestResult login(Map map) {
        String account = (String) map.get("userName");
        // 参数校验
        if (StringUtil.isEmpty(account)){
            return ResultUtils.error(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(),ResultCodeEnum.ILLEGAL_ARGUMENT.getMsg());
        }
        // 验证账户是否存在
        Admin admin = adminMapper.getByAuthenticatorUserName(account);
        if (null == admin){
            return ResultUtils.error(ResultCodeEnum.USER_NOT_FOUND.getCode(),ResultCodeEnum.USER_NOT_FOUND.getMsg());
        }
        // 验证密码
        String password = (String) map.get("password");
        if (!admin.getPassword().equals(EncryptUtils.encrypt(password,admin.getSalt()))){
            return ResultUtils.error(ResultCodeEnum.USER_PASSWORD_NOT_MATCH.getCode(),ResultCodeEnum.USER_PASSWORD_NOT_MATCH.getMsg());
        }
        // 验证是否禁用
        if (1 == admin.getStatus()){
            return ResultUtils.error(ResultCodeEnum.USER_BAN.getCode(),ResultCodeEnum.USER_BAN.getMsg());
        }

        WebLogAspect.getUser(admin);

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (DisabledAccountException e) {
            return ResultUtils.error(ResultCodeEnum.USER_BAN.getCode(),ResultCodeEnum.USER_BAN.getMsg());
        } catch (AuthenticationException e) {
            return ResultUtils.error(ResultCodeEnum.USER_PASSWORD_NOT_MATCH.getCode(),ResultCodeEnum.USER_PASSWORD_NOT_MATCH.getMsg());
        }

        // 生成 token
        Map domain = new HashMap();
        domain.put(AdminConstant.PKID, admin.getId());
        domain.put(AdminConstant.USERNAME, admin.getUserName());
        domain.put("companyName", admin.getCompanyName());
        domain.put("userType", admin.getUserType());
        domain.put("time", System.currentTimeMillis());
        String token = JWTUtils.generateToken(AdminConstant.JWT_SALT, domain);

        // 持久化到 Redis
        jwtRedisDAO.set(AdminConstant.JWT_TOKEN + account, token);

        // 返回数据
        Map adminMap = adminMapper.getAdminByUserName(account);
        Map params = new HashMap();
        params.put("admin",adminMap);
        params.put("token",token);

        // 修改最后登录时间
        Admin a = new Admin();
        a.setId(admin.getId());
        a.setLastLoginTime(DateUtil.getCurrentDate());
        adminMapper.updateByPrimaryKeySelective(a);

        return ResultUtils.success(params);
    }

    @Override
    public RestResult logout(String authorization) {

        Map params = JWTUtils.getClaims(AdminConstant.JWT_SALT, authorization);
        if (StringUtil.isEmpty((String) params.get(AdminConstant.USERNAME))){
            return ResultUtils.error(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(),ResultCodeEnum.ILLEGAL_ARGUMENT.getMsg());
        }
        // 更新 redis token
        jwtRedisDAO.delete(AdminConstant.JWT_TOKEN+params.get(AdminConstant.USERNAME));

        SecurityUtils.getSubject().logout();
        return ResultUtils.success(null);
    }

    @Override
    public RestResult saveAdmin(Admin admin) {

        // 判断账户是否已经存在
        if (null != adminMapper.getAdminByUserName(admin.getUserName())) {
            return ResultUtils.error(ResultCodeEnum.ACCOUNT_EXIST.getCode(),ResultCodeEnum.ACCOUNT_EXIST.getMsg());
        }
        // 判断邮箱是否已经存在
        if (null != adminMapper.selectByEmail(admin.getEmail())) {
            return ResultUtils.error(ResultCodeEnum.EMAIL_EXIST.getCode(),ResultCodeEnum.EMAIL_EXIST.getMsg());
        }

        // 添加权限管理员时才验证角色是否存在
//        if(admin.getAdminType() == 3) {
//            Role role = roleMapper.selectByPrimaryKey(admin.getRoleId());
//            if (null == role) {
//                throw new RoleNotExistException(ResultCodeEnum.ROLE_NOT_FOUND.getCode(), ResultCodeEnum.ROLE_NOT_FOUND.getMsg());
//            }
//        }

        Date date = DateUtil.getCurrentDate();

        // 初始化 管理员
        // 传递 date 作用：保持用户创建时间 与 用户角色关联信息创建时间一致性
        admin = formatAdmin(admin,date);

        adminMapper.insertSelective(admin);

        if (null == admin.getId()){
            throw new IllegalArgumentException();
        }

//        if(admin.getAdminType() == 3) {
//            // 持久化 用户角色关联信息
//            AdminRole adminRole = formatAdminRole(admin, admin.getId(), date);
//            adminRoleMapper.insertSelective(adminRole);
//        }

        // 处理 返回特殊内容
        admin.setPassword(null);
        admin.setSalt(null);
        return ResultUtils.success("注册成功，等待审核");
    }

    // 暂时没用
    @Override
    public RestResult saveBatch(List<Admin> adminRequests) {

        // 验证用户是否存在
        for (Admin a:adminRequests
             ) {
            if (null != adminMapper.getAdminByUserName(a.getUserName()))
                throw new APIRunTimeException(ResultCodeEnum.ACCOUNT_EXIST.getCode(),ResultCodeEnum.ACCOUNT_EXIST.getMsg());
        }

        // 验证角色是否存在
        for (Admin a : adminRequests) {
//            if(a.getAdminType() == 3) {
                if (null == roleMapper.selectByPrimaryKey(a.getRoleId()))
                    throw new RoleNotExistException(ResultCodeEnum.ROLE_NOT_FOUND.getCode(), ResultCodeEnum.ROLE_NOT_FOUND.getMsg());
//            }
        }

        List<Admin> admins = new ArrayList<>();
        Date currentDate = DateUtil.getCurrentDate();

        adminRequests.forEach(adminRequest -> admins.add(formatAdmin(adminRequest,currentDate)));

        // 批量持久化用户信息

        List<AdminRole> adminRoles = new ArrayList<>();
        for (int i=0; i<adminRequests.size(); i++){
//            if(adminRequests.get(i).getAdminType() == 3) {
                AdminRole adminRole = new AdminRole();
                adminRole.setAmdinId(admins.get(i).getId());
                adminRole.setRoleId(adminRequests.get(i).getRoleId());
                adminRole.setDeleteFlag(0);
                adminRole.setCreateTime(currentDate);
                adminRoles.add(adminRole);
//            }
        }

        // 批量持久化用户角色关联信息

        return ResultUtils.success(null);
    }

    @Override
    public RestResult deleteAdmin(List<Integer> pkIds) {
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
        adminMapper.deleteBatch(ids);
        // 管理员与角色关联信息 物理删除
//        adminRoleMapper.deleteBatch(ids);
        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult updateAdmin(Admin admin) {

        Date currentDate = DateUtil.getCurrentDate();

        adminMapper.updateByPrimaryKeySelective(admin);

        List<Integer> roleIds = adminRoleMapper.getAdminRoles(admin.getId());

//        if(admin.getAdminType() == 3) {
            // 删除之前 管理员角色关联信息
            adminRoleMapper.deleteByAdminId(admin.getId());

            // 添加新的关联信息
            AdminRole adminRole = formatAdminRole(admin, admin.getId(), currentDate);
            adminRoleMapper.insertSelective(adminRole);

            // 修改管理员信息时，如果有修改角色，那就清空用户的token，让用户重新登录
            if (!roleIds.isEmpty()) {
                for (int i = 0; i < roleIds.size(); i++) {
                    if (roleIds.get(i) != adminRole.getRoleId()) {
                        Admin a = adminMapper.selectByPrimaryKey(admin.getId());
                        jwtRedisDAO.delete(AdminConstant.JWT_TOKEN + a.getUserName());
                    }
                }
            }
//        }

        return ResultUtils.success("编辑成功");
    }

    @Override
    public RestResult getAdmin(Integer userId) {
        return ResultUtils.success(adminMapper.selectByPrimaryKey(userId));
    }

    @Override
    public RestResult listAdmin(String keyword, Integer userType, Integer pageNum, Integer pageSize) {

        int n = pageNum == 1 ? pageNum = 0 : (pageNum = (pageNum - 1) * pageSize);

        // 查询参数
        Map domainMap = new HashMap();
        domainMap.put(ParamConstant.KEY_WORD,keyword);
        domainMap.put("userType",userType);
        domainMap.put(ParamConstant.PAGE_SIZE,pageSize);
        domainMap.put(ParamConstant.PAGE_NUM,pageNum);

        List<Map> admins = adminMapper.listPage(domainMap);
        for (Map map:admins) {
            map.put("createTime", DateUtil.parseDate((Date) map.get("createTime")));
        }
        int count = adminMapper.countPage(domainMap);

        return ResultUtils.success(new PageResult<Map>(admins,count));
    }


    // ********* 自定义 封装功能函数 ***********

    public Admin formatAdmin(Admin admin, Date currentDate){
        admin.setDelStatus(0);
        admin.setCreateTime(currentDate);

        // 生成盐值 加密密码 并持久化管理员信息
        String salt = EncryptUtils.createSalt();
        String newPass = EncryptUtils.encrypt(admin.getPassword(), salt);
        String authenticator = EncryptUtils.encrypt(admin.getUserName(), salt);      // 散列加密（userName + salt） 用于 shiro 的身份认证
        admin.setPassword(newPass);
        admin.setSalt(salt);
        admin.setAuthenticator(authenticator);

        return admin;
    }

    public AdminRole formatAdminRole(Object param, Integer pkId, Date currentDate){
        AdminRole adminRole = new AdminRole();
        if (param instanceof Admin){
            Admin admin = (Admin) param;
            adminRole.setAmdinId(pkId);
            adminRole.setRoleId(admin.getRoleId());
            adminRole.setDeleteFlag(0);
            adminRole.setCreateTime(currentDate);
        }else if (param instanceof Admin){
            Admin admin = (Admin) param;
            adminRole.setAmdinId(pkId);
            adminRole.setRoleId(admin.getRoleId());
            adminRole.setDeleteFlag(0);
            adminRole.setCreateTime(currentDate);
        }
        return adminRole;
    }

    @Override
    public RestResult setStatus(Integer pkId){
        Admin admin = adminMapper.selectByPrimaryKey(pkId);
        if(admin.getStatus() == 0){// 设置禁用
            admin.setStatus(1);
        }else{ //设置启用
            admin.setStatus(0);
        }
        adminMapper.updateByPrimaryKeySelective(admin);

        jwtRedisDAO.delete(AdminConstant.JWT_TOKEN + admin.getUserName());

        return ResultUtils.success(admin);
    }

    @Override
    public RestResult changePassword(String userName, String oldPassword, String newPassword){
        Admin admin = adminMapper.getByAuthenticatorUserName(userName);
        String salt = admin.getSalt();
        String oldPass = EncryptUtils.encrypt(oldPassword, salt);
        if(!StringUtil.CheckIsEqual(oldPass, admin.getPassword())){
            return ResultUtils.error(1,"原密码输入错误，请重新输入");
        }
        String newPass = EncryptUtils.encrypt(newPassword, salt);
        admin.setPassword(newPass);
        adminMapper.updateByPrimaryKeySelective(admin);
        return ResultUtils.success("密码修改成功，请重新登录");
    }

    @Override
    public RestResult resetPassword(Integer pkId){
        Admin admin = adminMapper.selectByPrimaryKey(pkId);
        String salt = admin.getSalt();
        String newPass = EncryptUtils.encrypt("123456", salt);
        admin.setPassword(newPass);
        adminMapper.updateByPrimaryKeySelective(admin);
        return ResultUtils.success("密码重置成功");
    }

    @Override
    public Admin findByEmail(String email) {
        Admin admin = adminMapper.selectByEmail(email);
        return admin;
    }

    @Override
    public RestResult resetPassword(String userName, String newPassword) {
        Admin admin = adminMapper.selectByUserName(userName);
        if(admin == null){
            return ResultUtils.error(1,"用户名输入错误");
        }
        String salt = admin.getSalt();
        String newPass = EncryptUtils.encrypt(newPassword, salt);
        admin.setPassword(newPass);
        adminMapper.updateByPrimaryKeySelective(admin);

        return ResultUtils.success("密码修改成功");
    }

    @Override
    public RestResult findAllUser(){

        return ResultUtils.success(adminMapper.findAllUser());
    }
}
