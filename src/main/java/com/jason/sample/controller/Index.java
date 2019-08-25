package com.jason.sample.controller;

import com.jason.servlet.wrapper.Request;
import com.jason.servlet.wrapper.Response;

/**
 * author: zhangkai
 * date: 2019-08-25
 * description:
 */
public class Index {
    public void index(Request request, Response response) {
        request.attr("name", "hello");
        response.render("index");
    }

    public void hello(Request request, Response response) {
        response.text("hello");
    }

    public void html(Request request, Response response) {
        response.html("<h1>helloworld</h1>");
    }

}
