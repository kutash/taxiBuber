package com.kutash.taxibuber.websocket;

import com.google.gson.Gson;
import com.kutash.taxibuber.entity.Trip;
import com.kutash.taxibuber.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.OnError;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * The type Web socket sender.
 */
@ServerEndpoint(value = "/socket", configurator=UserAwareConfigurator.class)
public class WebSocketSender {

    private static final Logger LOGGER = LogManager.getLogger();
    private final static ReentrantLock lock = new ReentrantLock();
    private PushContext pushContext = PushContext.getInstance();

    /**
     * On open.
     *
     * @param session the session
     * @param config  the config
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        User user = (User) config.getUserProperties().get("currentUser");
        pushContext.add(session, user);
    }

    /**
     * On close.
     *
     * @param session the session
     */
    @OnClose
    public void onClose(Session session) {
        pushContext.remove(session);
    }

    /**
     * On error.
     *
     * @param session the session
     * @param t       the t
     */
    @OnError
    public void onError(Session session, Throwable t){
        pushContext.remove(session);
        LOGGER.log(Level.ERROR,t);
    }

    /**
     * Send.
     *
     * @param user    the user
     * @param message the message
     */
    public void send(User user, String message) {
        Set<Session> userSessions;
        lock.lock();
        try {
            userSessions = pushContext.getSessions().entrySet().stream()
                    .filter(e -> user.equals(e.getKey()))
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.toSet());
        }finally {
            lock.unlock();
        }

        for (Session userSession : userSessions) {
            if (userSession.isOpen()) {
                userSession.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * Send.
     *
     * @param user the user
     * @param trip the trip
     */
    public void send(User user, Trip trip) {
        Set<Session> userSessions;
        lock.lock();
        try {
            userSessions = pushContext.getSessions().entrySet().stream()
                    .filter(e -> user.equals(e.getKey()))
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.toSet());
        }finally {
            lock.unlock();
        }
        String message = new Gson().toJson(trip);
        for (Session userSession : userSessions) {
            if (userSession.isOpen()) {
                userSession.getAsyncRemote().sendText(message);
            }
        }
    }
}
