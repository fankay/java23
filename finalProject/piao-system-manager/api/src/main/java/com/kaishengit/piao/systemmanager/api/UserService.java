package com.kaishengit.piao.systemmanager.api;

import com.kaishengit.piao.systemmanager.modal.User;

import java.util.List;

public interface UserService {

    User findByUserName(String username);

    User findByMobile(String username);

    List<String> findRoleNamesByUserId(String id);
}
