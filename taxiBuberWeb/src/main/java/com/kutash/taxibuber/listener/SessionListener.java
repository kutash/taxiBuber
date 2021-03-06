package com.kutash.taxibuber.listener;

import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.websocket.WebSocketSender;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * The type Session listener.
 */
@WebListener
public class SessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        User user = (User) httpSessionEvent.getSession().getAttribute("currentUser");
        new WebSocketSender().send(user,"timeout");
    }
}
