package app.services;

import app.dtos.UserDtoResponse;
import app.models.GameSession;
import app.models.UserSession;


import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;


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
                GameSessionHandler.addActionToGameSession(GameMechanicsHandler::difineDamageDealer,
                        x -> x.getGameSessionId().equals(clientMessages[1]), session);
                GameSession gameSession = GameSessionHandler.findGameSessionByUserSession(session);
                if (gameSession != null) {
                    Map<Session, String> notifyObject = GameStatusNotifier.createGameNotification(gameSession, GameStatusNotifier.gameStatus.DAMAGE);
                    if (notifyObject != null) {
                        notifyObject.forEach((k, v) -> GameSessionHandler.sendToSession(k, v));
                    }

                }
        }
    }

    private UserSession createUserGameSession(Session session,EndpointConfig config) {

        HttpSession httpSession = getHttpSession(config);
        UserDtoResponse userDto = (UserDtoResponse) httpSession.getAttribute("user");
        httpSession.setAttribute("socketSessionId", session.getId());
        userDto.getUserHero().setSessionId(Integer.parseInt(session.getId()));
        return new UserSession(session, userDto);
    }
    private HttpSession getHttpSession(EndpointConfig config) {
        return (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
    }
}
