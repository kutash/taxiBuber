package com.kutash.taxibuber.controller;

import com.kutash.taxibuber.command.CommandEnum;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

@WebFilter(urlPatterns = { "/controller" }, servletNames = { "Controller" })
public class EditingFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.log(Level.INFO,"Edit user filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        CommandEnum command = CommandEnum.valueOf(req.getParameter("command").toUpperCase());
        EnumSet<CommandEnum> adminCommands = EnumSet.of(CommandEnum.EDIT);
        if (adminCommands.contains(command)) {
            if (session == null || session.getAttribute("currentUser") == null) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(req, resp);
                return;
            } else {
                User user = (User) session.getAttribute("currentUser");
                LOGGER.log(Level.INFO, "Admin filter user:{}", user);
                UserRole role = user.getRole();
                RequestDispatcher dispatcher;
                if (role.equals(UserRole.DRIVER) || role.equals(UserRole.CLIENT)) {
                    int currentUserId = user.getId();
                    int userId = Integer.parseInt(req.getParameter("userId"));
                    if (userId != currentUserId) {
                        dispatcher = request.getServletContext().getRequestDispatcher("/jsp/error/error403.jsp");
                        dispatcher.forward(req, resp);
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
    public void init(FilterConfig fConfig) throws ServletException {}
}
