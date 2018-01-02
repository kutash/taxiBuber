package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_ID = "userId";
    private UserService service;

    EditUserCommand(UserService service){this.service=service;}

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"edit user command");
        Router router = new Router();
        int id = Integer.parseInt(request.getParameter(USER_ID));
        User user = service.findById(id);
        List<Comment> comments = service.findComments(id);
        request.setAttribute("comments",comments);
        request.setAttribute("user",user);
        router.setPage(PageManager.getProperty("path.page.user"));
        return router;
    }
}
