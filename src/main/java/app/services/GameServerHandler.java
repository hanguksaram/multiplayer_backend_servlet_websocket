package app.services;

import app.dtos.UserDtoResponse;
import app.models.GameSession;

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
     //   this.httpSession = (HttpSession) config.getUserProperties()
     //           .get(HttpSession.class.getName());
     //   UserDtoResponse user = (UserDtoResponse) this.httpSession.getAttribute("user");
     //   try{session.getBasicRemote().sendText(user.getName());}
      //  catch (Exception ex) {}
        GameSessionHandler.startGame(session);
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
}
