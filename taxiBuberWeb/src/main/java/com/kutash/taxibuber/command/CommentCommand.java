package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class CommentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMENT = "comment";
    private static final String VALUATION = "valuation";
    private static final String CURRENT_USER = "currentUser";
    private static final String USER_ID = "userId";
    private static final String LANGUAGE = "language";
    private static final String NUMBER = "number";
    private UserService userService;

    CommentCommand(UserService userService){
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        HttpSession session = request.getSession();
        User reviewer = (User) session.getAttribute(CURRENT_USER);
        String language = (String) session.getAttribute(LANGUAGE);
        String text = request.getParameter(COMMENT);
        String mark = request.getParameter(VALUATION);
        int userId = Integer.parseInt(request.getParameter(USER_ID));
        if (StringUtils.isNotEmpty(text) && text.length() <= 1000 && StringUtils.isNotEmpty(mark)){
            byte valuation = Byte.parseByte(request.getParameter(VALUATION));
            Comment comment = new Comment(text,userId,reviewer.getId(),new Date(),valuation);
            int result = userService.createComment(comment);
            if (result > 0){
                userService.changeRating(userId);
                router.setRoute(Router.RouteType.REDIRECT);
                session.setAttribute("isCreated",true);
                router.setPage("controller?command=trips");
            }
        }else {
            session.setAttribute("wrongComment",new MessageManager(language).getProperty("message.wrongcomment"));
            router.setPage("controller?command=trips");
        }
        return router;
    }
}
