package app.services;

import app.dtos.UserDtoResponse;
import app.models.GameSession;
import app.models.UserSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint(value = "/duelsession", configurator = HttpSessionConfigurator.class)
public class GameServerHandler {

   // @Inject
   // private GameSessionHandler gameSessionsHandler;
    private HttpSession httpSession;
    @OnOpen
    public void open(Session session, EndpointConfig config) {

        GameSessionHandler.startGame(this.createUserGameSession(session, config));
    }

    @OnClose
    public void close(Session session) {
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
    }

    private UserSession createUserGameSession(Session session,EndpointConfig config) {
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        UserDtoResponse userDto = (UserDtoResponse) this.httpSession.getAttribute("user");
        return new UserSession(session, userDto);
    }
}
