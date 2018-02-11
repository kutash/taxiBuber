package com.kutash.taxibuber.filter;

import com.kutash.taxibuber.command.CommandEnum;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.entity.UserRole;
import com.kutash.taxibuber.resource.PageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

/**
 * The type Security filter edit.
 */
@WebFilter(urlPatterns = { "/controller" }, servletNames = { "Controller" })
public class SecurityFilterEdit implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.log(Level.DEBUG,"Editing filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        CommandEnum command = CommandEnum.valueOf(req.getParameter("command").toUpperCase());
        EnumSet<CommandEnum> adminCommands = EnumSet.of(CommandEnum.DELETE_CAR,CommandEnum.UPDATE_USER);
        if (adminCommands.contains(command)) {
            if (session == null || session.getAttribute("currentUser") == null) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PageManager.getProperty("path.page.index"));
                dispatcher.forward(req, resp);
                return;
            } else {
                User user = (User) session.getAttribute("currentUser");
                UserRole role = user.getRole();
                RequestDispatcher dispatcher;
                if (role.equals(UserRole.DRIVER) || role.equals(UserRole.CLIENT)) {
                    int currentUserId = user.getId();
                    int userId = Integer.parseInt(req.getParameter("userId"));
                    if (userId != currentUserId) {
                        dispatcher = request.getServletContext().getRequestDispatcher(PageManager.getProperty("path.page.error403"));
                        dispatcher.forward(req, resp);
                        return;
                    }
                }else if(role.equals(UserRole.ADMIN)){
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
