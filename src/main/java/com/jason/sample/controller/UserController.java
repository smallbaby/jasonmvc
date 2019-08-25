package com.jason.sample.controller;

import com.jason.Mario;
import com.jason.db.MarioDb;
import com.jason.sample.model.User;
import com.jason.servlet.wrapper.Request;
import com.jason.servlet.wrapper.Response;

import java.util.List;

/**
 * author: zhangkai
 * date: 2019-08-25
 * description:
 */
public class UserController {

    public void users(Request request, Response response) {
        List<User> users = MarioDb.getList("select * from t_user", User.class, null);
        request.attr("users", users);
        response.render("users");

    }

}
