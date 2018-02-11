package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.exception.DAOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The interface Command.
 */
public interface Command {
    /**
     * Execute router.
     *
     * @param request  the request
     * @param response the response
     * @return the router
     */
    Router execute(HttpServletRequest request, HttpServletResponse response);
}
