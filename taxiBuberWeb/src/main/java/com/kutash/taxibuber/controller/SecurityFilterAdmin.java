package com.kutash.taxibuber.controller;

import com.kutash.taxibuber.command.CommandEnum;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

@WebFilter(urlPatterns = { "/controller","/ajaxController" }, servletNames = { "Controller","ajaxController" })
public class SecurityFilterAdmin implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LANGUAGE = "language";
    private static final String COMMAND = "command";

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
        LOGGER.log(Level.DEBUG,"Admin filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String action = req.getParameter(COMMAND);
        String language = (String) req.getSession().getAttribute(LANGUAGE);
        CommandEnum command;
        if (StringUtils.isEmpty(action)){
            request.setAttribute("wrongAction", action + new MessageManager(language).getProperty("message.wrongaction"));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PageManager.getProperty("path.page.error"));
            dispatcher.forward(req, resp);
            return;
        }
        try {
            command = CommandEnum.valueOf(action.toUpperCase());
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action + new MessageManager(language).getProperty("message.wrongaction"));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PageManager.getProperty("path.page.error"));
            dispatcher.forward(req, resp);
            return;
        }
        EnumSet<CommandEnum> adminCommands = EnumSet.of(CommandEnum.SHOW_USERS,CommandEnum.BAN,CommandEnum.DELETE);
        if (adminCommands.contains(command)) {
                if (session == null || session.getAttribute("currentUser") == null) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PageManager.getProperty("path.page.index"));
                dispatcher.forward(req, resp);
                return;
            } else {
                User user = (User) session.getAttribute("currentUser");
                LOGGER.log(Level.DEBUG, "Admin filter user:{}", user);
                UserRole role = user.getRole();
                RequestDispatcher dispatcher;
                if (!role.equals(UserRole.ADMIN)) {
                    dispatcher = request.getServletContext().getRequestDispatcher(PageManager.getProperty("path.page.error403"));
                    dispatcher.forward(req, resp);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {}
}