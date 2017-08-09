package com.kaishengit.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm implements Realm {
    public String getName() {
        return "my-realm";
    }

    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    /**
     * 认证的方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //获取账号
        String name = token.getUsername();
        //获取密码
        String password = new String(token.getPassword());
        if(!"tom".equals(name)) {
            throw new UnknownAccountException("不是Tom");
        }
        if(!"000000".equals(password)) {
            throw new IncorrectCredentialsException("账号或密码错误");
        }

        return new SimpleAuthenticationInfo(name,password,getName());
    }
}
