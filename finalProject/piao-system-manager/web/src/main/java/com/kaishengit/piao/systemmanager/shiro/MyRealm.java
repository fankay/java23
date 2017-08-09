package com.kaishengit.piao.systemmanager.shiro;

import com.kaishengit.piao.systemmanager.api.UserService;
import com.kaishengit.piao.systemmanager.modal.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

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
