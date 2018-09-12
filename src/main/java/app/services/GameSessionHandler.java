package app.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;


import app.dtos.GameStatusNotifier;
import app.models.GameSession;
import app.models.UserSession;

@ApplicationScoped
@Default
public class GameSessionHandler {
    private static final LinkedBlockingQueue<UserSession> sessionAccumulator = new LinkedBlockingQueue<UserSession>();
    private static final ConcurrentMap<String, GameSession> sessionGame = new ConcurrentHashMap<String, GameSession>();

    public static void startGame(UserSession session)  {

        try{
            sessionAccumulator.put(session);
            createGameSession();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    private static void createGameSession(){
        if (sessionAccumulator.size() % 2 == 0) {
            UserSession sessionOne = sessionAccumulator.poll();
            UserSession sessionTwo = sessionAccumulator.poll();
            String id = String.valueOf(new Date().getTime());
            GameSession gameSession = new GameSession(sessionOne, sessionTwo);
            sessionGame.putIfAbsent(id, gameSession);
            Map<String, Session> notifyObject = GameStatusNotifier.createGameNotification(id, gameSession, GameStatusNotifier.gameStatus.START);
            if (notifyObject != null) {
                notifyObject.forEach((k, v) -> sendToSession(v, k));
            }
        }
    }
    private void setTimerBeforeGameStart(String id, GameSession gameSession) {

    }
    private static void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {


        }
    }

}
