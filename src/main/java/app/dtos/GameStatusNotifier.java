package app.dtos;

import app.models.GameSession;
import app.models.UserSession;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class GameStatusNotifier {

    private static JsonProvider provider = JsonProvider.provider();

    public static Map<String, Session> createGameNotification(String gameId, GameSession gameSession, GameStatusNotifier.gameStatus status) {

        Map<String, Session> notifyObject = new HashMap<String, Session>();

        switch (status) {
            case START:
                String message = createJsonMessage(gameId, gameStatus.START, gameSession.getSessionTwo());
                notifyObject.put(message, gameSession.getSessionOne().getSession());
                message = createJsonMessage(gameId, gameStatus.START, gameSession.getSessionOne());
                notifyObject.put(message, gameSession.getSessionTwo().getSession());
                return notifyObject;

            default:
                return null;
        }
    }
    private static String createJsonMessage(String gameId, GameStatusNotifier.gameStatus status, UserSession session) {
        JsonObject gameObject = provider.createObjectBuilder()
                .add("gameId", gameId)
                .add("status", status.toString())
                .add("enemyNickname", session.getUserDto().getName())
                .add("enemyRating", session.getUserDto().getUserHero().getRating())
                .build();
        return gameObject.toString();
    }

    public enum gameStatus {
        START, PAUSE, END;

        @Override public String toString() {
            String s = super.toString();
            return s.toLowerCase();
        }
    }
}
