package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderCommand implements Command {

    private OrderService service;

    OrderCommand(OrderService orderService) {
        this.service = orderService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
