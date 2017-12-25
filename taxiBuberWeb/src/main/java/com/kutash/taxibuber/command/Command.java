package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    Router execute(HttpServletRequest request, HttpServletResponse response);
}
