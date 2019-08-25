package com.jason.sample;

import com.jason.Bootstrap;
import com.jason.Mario;
import com.jason.db.MarioDb;
import com.jason.sample.controller.Index;
import com.jason.sample.controller.UserController;

/**
 * author: zhangkai
 * date: 2019-08-25
 * description:
 */
public class App implements Bootstrap {
    @Override
    public void init(Mario mario) {
        Index index = new Index();
        UserController userController = new UserController();
        mario.addRoute("/", "index", index);
        mario.addRoute("/hello", "hello", index);
        mario.addRoute("/html", "html", index);

        mario.addRoute("/users", "users", userController);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 配置数据库
        MarioDb.init("jdbc:mysql://127.0.0.1:3306/mario_sample", "root", "123456");
    }
}
