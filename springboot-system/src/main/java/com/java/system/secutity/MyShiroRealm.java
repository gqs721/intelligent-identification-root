package com.java.system.secutity;

import com.java.common.utils.StringUtil;
import com.java.model.dao.AdminMapper;
import com.java.model.dao.ResourceMapper;
import com.java.model.dao.RoleMapper;
import com.java.model.domain.Admin;
import com.java.model.domain.Resource;
import com.java.model.domain.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());

        // 从数据库获取对应用户名密码的用户
        Admin admin = adminMapper.getByAuthenticatorUserName(name);
        if (admin != null) {
            // 用户为禁用状态
            if (admin.getStatus() == 1) {
                throw new DisabledAccountException();
            }
            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    admin, //用户
                    password, //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (principal instanceof Admin) {
            Admin admin = (Admin) principal;
//            Admin admin = adminMapper.getByAuthenticatorUserName(account);

            List<String> roleNames = new ArrayList<>();
            List<Integer> roleIds = new ArrayList<>();

            // 权限列表
            List<String> perms = new ArrayList<>();

            if (null != admin){
                // 获取当前 管理员 角色列表
                List<Role> roles = new ArrayList<>();

                if(admin.getUserType() != 1){
                    roles = roleMapper.getRolesByAdminId(admin.getId());
                }else{
                    roles = roleMapper.getAllRole();
                }

                // 获取 角色名称列表、角色ID列表
                for (Role role: roles) {
                    roleNames.add(role.getUkName());
                    roleIds.add(role.getPkId());
                }

                if (roleIds.size() > 0){
                    // 获取当前角色下的 资源列表
                    List<Resource> resources = new ArrayList<>();
                    if(admin.getUserType() != 1) {
                        resources = resourceMapper.getResourcesByRoleId(roleIds);
                    }else{
                        resources = resourceMapper.getAllMenus();
                    }

                    for (Resource resource:resources) {
                        if (null != resource){
                            if (!StringUtil.isEmpty(resource.getPerms())){
                                if (resource.getPerms().contains(",")) {
                                    String[] permsArray = resource.getPerms().split(",");
                                    perms.addAll(Arrays.asList(permsArray));
                                } else {
                                    perms.add(resource.getPerms());
                                }
                            }
                        }
                    }
                }

            }else {
                throw new AuthorizationException();
            }

            //为当前用户设置角色和权限
            authorizationInfo.addRoles(roleNames);
            authorizationInfo.addStringPermissions(perms);
        }
//        logger.info("---- 获取到以下权限 ----");
//        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;
    }

}
