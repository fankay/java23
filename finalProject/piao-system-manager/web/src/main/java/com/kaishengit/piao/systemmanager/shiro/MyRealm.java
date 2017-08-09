package com.kaishengit.piao.systemmanager.shiro;

import com.kaishengit.piao.systemmanager.api.UserService;
import com.kaishengit.piao.systemmanager.modal.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

public class MyRealm extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 权限认证方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录的用户
        User user = (User) principalCollection.getPrimaryPrincipal();
        //获取当前登录的用户拥有的角色名称集合
        List<String> roleNames = userService.findRoleNamesByUserId(user.getId());
        //判断集合是否有值
        if(!roleNames.isEmpty()) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            //添加角色列表
            authorizationInfo.addRoles(roleNames);
            //添加权限列表
            //authorizationInfo.addStringPermissions();
            return authorizationInfo;
        }
        return null;
    }

    /**
     * 登录认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String mobile = token.getUsername();
        User user = userService.findByMobile(mobile);
        if(user == null) {
            throw new UnknownAccountException("没有["+mobile+"]对应的账号");
        } else if(User.STATE_DISABLE.equals(user.getState())) {
            throw new LockedAccountException("该账号已被禁用");
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
    }
}
