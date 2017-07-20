package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;

import javax.servlet.http.HttpSession;

public class BaseController {

    public Account getCurrUser(HttpSession session) {
        return (Account) session.getAttribute("curr_user");
    }

}
