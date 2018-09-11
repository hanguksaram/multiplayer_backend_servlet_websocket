package app.services;

import app.models.GameSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/duelfield")
public class GameServerHandler {

    @Inject
    private GameSessionHandler gameSessionsHandler;
    @OnOpen
    public void open(Session session) {
        gameSessionsHandler.startGame(session);
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
