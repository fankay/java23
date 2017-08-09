package com.kaishengit.piao.systemmanager.shiro;

import com.kaishengit.piao.systemmanager.modal.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

public class ShiroUtil {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static User getCurrentUser() {
        return (User) getSubject().getPrincipal();
    }

    public static boolean hasRole(String roleName) {
        return getSubject().hasRole(roleName);
    }

    public static boolean hasAllRole(String... roleNames) {
        return getSubject().hasAllRoles(Arrays.asList(roleNames));
    }

}
