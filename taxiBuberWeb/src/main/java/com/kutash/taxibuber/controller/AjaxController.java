package com.kutash.taxibuber.controller;

import com.kutash.taxibuber.command.Command;
import com.kutash.taxibuber.command.CommandFactory;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(value = "/buber")
public class AjaxController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        LOGGER.log(Level.INFO,"processRequest method");
        CommandFactory client = new CommandFactory();
        Command command = client.defineCommand(request);
        Router router = command.execute(request,response);
        if (router != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(router.getPage());
        }else {
            String page = PageManager.getProperty("path.page.error");
            String language = (String) request.getSession().getAttribute("language");
            request.getSession().setAttribute("nullPage", new MessageManager(language).getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
