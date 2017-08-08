package com.kaishengit.piao.systemmanager.api;

import com.kaishengit.piao.systemmanager.modal.User;

public interface UserService {

    User login(String userName,String password);

}
