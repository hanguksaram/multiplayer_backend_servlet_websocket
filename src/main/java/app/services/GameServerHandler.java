package app.services;

import app.dtos.UserDtoResponse;
import app.models.GameSession;
import app.models.UserSession;
import org.jboss.weld.context.http.Http;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;

@ApplicationScoped
@ServerEndpoint(value = "/duelsession", configurator = HttpSessionConfigurator.class)
public class GameServerHandler {

   // @Inject
   // private GameSessionHandler gameSessionsHandler;

    @OnOpen
    public void open(Session session, EndpointConfig config) {

        String gameSessionId = GameSessionHandler.startGame(this.createUserGameSession(session, config));
        if (gameSessionId != null) {
            HttpSession httpSession = getHttpSession(config);
            httpSession.setAttribute("gameSessionId", gameSessionId);
        }
    }

    @OnClose
    public void close(Session session) {
        GameSessionHandler.removeSession(session.getId());
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {


        String[] clientMessages = JsonHandler.readPropertyFromObject(message, "status", "gameSessionId");
        switch(clientMessages[0]){
            case "damage":
                GameSessionHandler.addActionToGameSession(GameMechanicsHandler::calculateDamage,
                        x -> x.getGameSessionId().equals(clientMessages[1]));
        }
    }

    private UserSession createUserGameSession(Session session,EndpointConfig config) {

        HttpSession httpSession = getHttpSession(config);
        UserDtoResponse userDto = (UserDtoResponse) httpSession.getAttribute("user");
        httpSession.setAttribute("socketSessionId", session.getId());
        return new UserSession(session, userDto);
    }
    private HttpSession getHttpSession(EndpointConfig config) {
        return (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
    }
}
