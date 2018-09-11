package app.services;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;


import app.models.GameSession;


public class GameSessionHandler {
    private final LinkedBlockingQueue<Session> sessionAccumulator = new LinkedBlockingQueue<Session>();
    private final ConcurrentMap<String, GameSession> sessionGame = new ConcurrentHashMap<String, GameSession>();

    public void startGame(Session session)  {
        try{
            sessionAccumulator.put(session);
            createGameSession();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    private void createGameSession(){
        if (sessionAccumulator.size() % 2 == 0) {
            Session sessionOne = sessionAccumulator.poll();
            Session sessionTwo = sessionAccumulator.poll();
            String id = String.valueOf(new Date().getTime());
            sessionGame.putIfAbsent(id, new GameSession(sessionOne, sessionTwo));
            JsonProvider provider = JsonProvider.provider();
            JsonObject gameId = provider.createObjectBuilder()
                    .add("gameId", id).build();
            sendToSession(sessionOne, gameId);
            sendToSession(sessionTwo, gameId);
        }
    }
    private void setTimerBeforeGameStart(String id, GameSession gameSession) {

    }
    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {


        }
    }
}
