package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.resource.PageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        router.setPage(PageManager.getProperty("path.page.error"));
        return router;
    }
}
