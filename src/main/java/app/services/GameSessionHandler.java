package app.services;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import app.models.GameSession;
import app.models.UserSession;



public class GameSessionHandler {
    private static LinkedBlockingQueue<UserSession> sessionAccumulator = new LinkedBlockingQueue<>();
    private static CopyOnWriteArrayList<GameSession> sessionGame = new CopyOnWriteArrayList<>();

    public static String startGame(UserSession session)  {

        try{
            sessionAccumulator.put(session);
            if (sessionAccumulator.size() % 2 == 0)
                return createGameSession();

        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    private static String createGameSession(){

            UserSession sessionOne = sessionAccumulator.poll();
            UserSession sessionTwo = sessionAccumulator.poll();
            String id = String.valueOf(new Date().getTime());
            GameSession gameSession = new GameSession(id ,sessionOne, sessionTwo);
            sessionGame.add(gameSession);
            Map<Session, String> notifyObject = GameStatusNotifier.createGameNotification(gameSession, GameStatusNotifier.gameStatus.START);
            if (notifyObject != null) {
                notifyObject.forEach((k, v) -> sendToSession(k, v));
            }
            return id;

    }
    private void setTimerBeforeGameStart(String id, GameSession gameSession) {

    }
    public static void handleDamageDeal(String gameSessionId, String userDamageDealerId) {

    }
    public static void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }
    public static void removeSession(String sessionId){
        sessionAccumulator = sessionAccumulator.stream().filter(x -> x.getSession().equals(sessionId))
                .collect(Collectors.toCollection(LinkedBlockingQueue<UserSession>::new));
        GameSession gameSession = sessionGame.stream().filter( x -> x.getSessionOne().getSessionId().equals(sessionId) ||
                x.getSessionTwo().getSessionId().equals(sessionId)).findFirst().orElse(null);
        if (gameSession != null){

            //if(!gameSession.getSessionTwo().getSessionId().equals(sessionId)) {
              //  sendToSession(gameSession.getSessionTwo(),    );
            //}
            Map<Session, String> notifyObject = GameStatusNotifier.createGameNotification(gameSession,
                    GameStatusNotifier.gameStatus.CONNECTIONLOST);
            notifyObject.forEach((k, v) -> sendToSession(k, v));
            sessionGame = sessionGame.stream().filter(x -> !x.getSessionOne().getSessionId().equals(sessionId) &&
                    !x.getSessionTwo().getSessionId().equals(sessionId))
                    .collect(Collectors.toCollection(CopyOnWriteArrayList<GameSession>::new));
        }


    }
    public static void addActionToGameSession(BiConsumer<GameSession, Session> func, Predicate<GameSession> pred, Session session){
        sessionGame.forEach(k -> {
            if (pred.test(k))
                func.accept(k, session);

        });
    }
    public static GameSession findGameSessionByUserSession(Session userSession) {
        GameSession gameSession = sessionGame.stream().filter(x -> x.getSessionTwo().getSessionId().equals(userSession.getId())
                || x.getSessionOne().getSessionId().equals(userSession.getId())).findFirst().orElse(null);
        return gameSession;

    }



}
