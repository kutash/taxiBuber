package com.kutash.taxibuber.command;

import com.kutash.taxibuber.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    public Command defineCommand(HttpServletRequest request) {
        String language = (String) request.getSession().getAttribute("language");
        Command current = new ErrorCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            request.setAttribute("wrongAction", action + new MessageManager(language).getProperty("message.wrongaction"));
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action + new MessageManager(language).getProperty("message.wrongaction"));
        }
        return current;
    }
}
