package com.kutash.taxibuber.websocket;

import com.kutash.taxibuber.entity.User;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * The type User aware configurator.
 */
public class UserAwareConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        User user = (User) httpSession.getAttribute("currentUser");
        config.getUserProperties().put("currentUser", user);
    }

}
