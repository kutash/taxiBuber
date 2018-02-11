package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.MessageManager;
import com.kutash.taxibuber.resource.PageManager;
import com.kutash.taxibuber.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * The type Comment command.
 */
public class CommentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMENT = "comment";
    private static final String VALUATION = "valuation";
    private static final String CURRENT_USER = "currentUser";
    private static final String USER_ID = "userId";
    private static final String LANGUAGE = "language";
    private static final short MAX_LENGTH = 1000;
    private UserService userService;

    /**
     * Instantiates a new Comment command.
     *
     * @param userService the user service
     */
    CommentCommand(UserService userService){
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"Saving comment");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.removeAttribute("isCreated");
        User reviewer = (User) session.getAttribute(CURRENT_USER);
        String language = (String) session.getAttribute(LANGUAGE);
        String text = request.getParameter(COMMENT);
        String mark = request.getParameter(VALUATION);
        int userId = Integer.parseInt(request.getParameter(USER_ID));
        if (StringUtils.isNotEmpty(text) && text.length() <= MAX_LENGTH && StringUtils.isNotEmpty(mark)){
            byte valuation = Byte.parseByte(request.getParameter(VALUATION));
            Comment comment = new Comment(text,userId,reviewer.getId(),new Date(),valuation);
            userService.createComment(comment);
            router.setRoute(Router.RouteType.REDIRECT);
            session.setAttribute("isCreated",true);

        }else {
            request.setAttribute("wrongComment",new MessageManager(language).getProperty("message.wrongcomment"));
        }
        router.setPage(PageManager.getProperty("path.command.trips"));
        return router;
    }
}
