package app.services;

import app.models.GameSession;
import app.models.UserSession;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class GameStatusNotifier {

    private static JsonProvider provider = JsonProvider.provider();

    public static Map<Session, String> createGameNotification(GameSession gameSession, GameStatusNotifier.gameStatus status) {

        Map<Session, String> notifyObject = new HashMap<>();

        switch (status) {
            case START:
                String message = createStatusGameMessage(gameSession.getGameSessionId(), gameStatus.START, gameSession.getSessionTwo());
                notifyObject.put(gameSession.getSessionOne().getSession(), message);
                message = createStatusGameMessage(gameSession.getGameSessionId(), gameStatus.START, gameSession.getSessionOne());
                notifyObject.put(gameSession.getSessionTwo().getSession(), message);
                return notifyObject;
            case CONNECTIONLOST:
                message = createStatusGameMessage(gameSession.getGameSessionId(), gameStatus.CONNECTIONLOST, gameSession.getSessionTwo());
                notifyObject.put(gameSession.getSessionOne().getSession(), message);
                message = createStatusGameMessage(gameSession.getGameSessionId(), gameStatus.START, gameSession.getSessionOne());
                notifyObject.put(gameSession.getSessionTwo().getSession(), message);
                return notifyObject;
            case DAMAGE:
                message = JsonHandler.createJsonMessage(status, gameSession.getSessionOne().getUserDto().getUserHero(), gameSession.getSessionTwo().getUserDto().getUserHero());
                notifyObject.put(gameSession.getSessionOne().getSession(), message);
                notifyObject.put(gameSession.getSessionTwo().getSession(), message);
                return notifyObject;

            default:
                return null;
        }
    }
    private static String createStatusGameMessage(String gameId, GameStatusNotifier.gameStatus status, UserSession session) {
        JsonObject gameObject = provider.createObjectBuilder()
                .add("gameId", gameId)
                .add("sessionId", session.getSession().getId())
                .add("isTurn", "false")
                .add("status", status.toString())
                .add("enemyNickname", session.getUserDto().getName())
                .add("enemyRating", session.getUserDto().getUserHero().getRating())
                .build();
        return gameObject.toString();
    }

    public enum gameStatus {
        START, PAUSE, END, CONNECTIONLOST, DAMAGE;

        @Override public String toString() {
            String s = super.toString();
            return s.toLowerCase();
        }
    }
}
