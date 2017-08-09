package com.kaishengit.piao.systemmanager.api;

import com.kaishengit.piao.systemmanager.modal.User;

public interface UserService {

    User findByUserName(String username);

    User findByMobile(String username);
}
