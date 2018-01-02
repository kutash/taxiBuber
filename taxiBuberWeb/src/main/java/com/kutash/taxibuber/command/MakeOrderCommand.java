package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("making order");
        return null;
    }
}
