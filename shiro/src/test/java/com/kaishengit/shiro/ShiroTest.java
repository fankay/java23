package com.kaishengit.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

public class ShiroTest {

    @Test
    public void helloWorld() {

        //1. 读取classpath中的shiro.ini配置文件，并创建securityManagerFactory对象
        Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2. 获取SecurityManager
        SecurityManager securityManager = securityManagerFactory.getInstance();
        //3. 设置SecurityManager(仅设置一次)
        SecurityUtils.setSecurityManager(securityManager);

        //4.获取当前登录的对象
        Subject subject = SecurityUtils.getSubject();

        //5.根据账号和密码进行登录
        UsernamePasswordToken token = new UsernamePasswordToken("tom","0000");
        try {
            //6. 登录
            subject.login(token);
        } catch (UnknownAccountException ex) {
            ex.printStackTrace();
            System.out.println("找不到该账号");
        } catch (LockedAccountException ex) {
            System.out.println("账号被冻结异常");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("账号或密码错误异常");
        } catch (AuthenticationException ex) {
            System.out.println("认证异常");
        }
        //7. 安全退出
        subject.logout();

    }

    @Test
    public void userMyRealm() {
        //1. 读取classpath中的shiro.ini配置文件，并创建securityManagerFactory对象
        Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        //2. 获取SecurityManager
        SecurityManager securityManager = securityManagerFactory.getInstance();
        //3. 设置SecurityManager(仅设置一次)
        SecurityUtils.setSecurityManager(securityManager);

        //4.获取当前登录的对象
        Subject subject = SecurityUtils.getSubject();

        //5.根据账号和密码进行登录
        UsernamePasswordToken token = new UsernamePasswordToken("aa","000000");
        try {
            //6. 登录
            subject.login(token);
        } catch (UnknownAccountException ex) {
            ex.printStackTrace();
            System.out.println("找不到该账号");
        } catch (LockedAccountException ex) {
            System.out.println("账号被冻结异常");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("账号或密码错误异常");
        } catch (AuthenticationException ex) {
            System.out.println("认证异常");
        }
        //7. 安全退出
        subject.logout();
    }

    @Test
    public void checkRole() {
        //1. 读取classpath中的shiro.ini配置文件，并创建securityManagerFactory对象
        Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-roles.ini");
        //2. 获取SecurityManager
        SecurityManager securityManager = securityManagerFactory.getInstance();
        //3. 设置SecurityManager(仅设置一次)
        SecurityUtils.setSecurityManager(securityManager);

        //4.获取当前登录的对象
        Subject subject = SecurityUtils.getSubject();

        //5.根据账号和密码进行登录
        UsernamePasswordToken token = new UsernamePasswordToken("jack","000000");
        try {
            //6. 登录
            subject.login(token);

            //7. 判断是否是CTO  hasRole方法判断用户是否有角色
            /*System.out.println("CTO ? " + subject.hasRole("cto"));
            System.out.println("admin ? " + subject.hasRole("admin"));

            boolean isCtoAndAdmin = subject.hasAllRoles(Arrays.asList("cto","admin"));//判断用是否是 cto+admin
            System.out.println("cto + admin?" + isCtoAndAdmin);

            boolean[] results = subject.hasRoles(Arrays.asList("sales","hr","cto","admin"));
            for (boolean result : results) {
                System.out.println(result);
            }*/

            //7. 校验subject是否有角色hr，如果没有则抛出异常
            //subject.checkRole("hr");

            //8.判断用户是否具有某个权限
            //System.out.println("user:query ?"+ subject.isPermitted("user:query"));
            //判断用户是否有该权限，没有则异常
            subject.checkPermission("user:query");


        } catch (UnknownAccountException ex) {
            ex.printStackTrace();
            System.out.println("找不到该账号");
        } catch (LockedAccountException ex) {
            System.out.println("账号被冻结异常");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("账号或密码错误异常");
        } catch (AuthenticationException ex) {
            System.out.println("认证异常");
        }
        //7. 安全退出
        subject.logout();
    }

}
