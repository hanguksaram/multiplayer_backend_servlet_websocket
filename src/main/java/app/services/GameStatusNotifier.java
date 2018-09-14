package app.services;

import app.dtos.StatusGameMessageDto;
import app.models.GameSession;
import app.models.UserSession;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class GameStatusNotifier {

    public static Map<Session, String> createGameNotification(GameSession gameSession) {

        Map<Session, String> notifyObject = new HashMap<>();

        switch (gameSession.getGameStatus()) {
            case START:
                int firstPlayerTurn = 0;
                int secondPlayerTurn = 0;
                if (GameMechanicsHandler.defineStartingPlayer() == 1)
                    secondPlayerTurn = 1;
                else
                    firstPlayerTurn = 1;
                String message = null;
                StatusGameMessageDto messageDto = new StatusGameMessageDto(gameSession.getGameSessionId(), gameSession.getSessionOne().getSessionId(), firstPlayerTurn, gameSession.getGameStatus(),
                        gameSession.getSessionTwo().getUserDto().getName(), gameSession.getSessionTwo().getUserDto().getUserHero().getRating(),
                        gameSession.getSessionTwo().getUserDto().getUserHero().getHealth(), gameSession.getSessionTwo().getUserDto().getUserHero().getDamageMultiplier());
                message = JsonHandler.createJsonMessage(gameSession.getGameStatus(), messageDto);
                notifyObject.put(gameSession.getSessionOne().getSession(), message);
                messageDto = new StatusGameMessageDto(gameSession.getGameSessionId(),  gameSession.getSessionTwo().getSessionId(), secondPlayerTurn, gameSession.getGameStatus(), gameSession.getSessionOne().getUserDto().getName(), gameSession.getSessionOne().getUserDto().getUserHero().getRating(),
                        gameSession.getSessionOne().getUserDto().getUserHero().getHealth(), gameSession.getSessionOne().getUserDto().getUserHero().getDamageMultiplier());
                message = JsonHandler.createJsonMessage(gameSession.getGameStatus(), messageDto);
                notifyObject.put(gameSession.getSessionTwo().getSession(), message);
                return notifyObject;
            case CONNECTIONLOST:
                message = JsonHandler.createJsonMessage(gameSession.getGameStatus(), new StatusGameMessageDto(gameSession.getGameStatus()));
                notifyObject.put(gameSession.getSessionOne().getSession(), message);
                notifyObject.put(gameSession.getSessionTwo().getSession(), message);
                return notifyObject;
            case DAMAGE:
                message = JsonHandler.createJsonMessage(gameSession.getGameStatus(), gameSession.getSessionOne().getUserDto().getUserHero(), gameSession.getSessionTwo().getUserDto().getUserHero());
                notifyObject.put(gameSession.getSessionOne().getSession(), message);
                notifyObject.put(gameSession.getSessionTwo().getSession(), message);
                return notifyObject;
            case END:
                message = JsonHandler.createJsonMessage(gameSession.getGameStatus(), gameSession.getSessionOne().getUserDto().getUserHero(), gameSession.getSessionTwo().getUserDto().getUserHero());
                notifyObject.put(gameSession.getSessionOne().getSession(), message);
                notifyObject.put(gameSession.getSessionTwo().getSession(), message);
                return notifyObject;

            default:
                return null;
        }
    }
//    private static String createStatusGameMessage(String gameId, GameStatusNotifier.gameStatus status, UserSession session) {
//        JsonObject gameObject = provider.createObjectBuilder()
//                .add("gameId", gameId)
//                .add("sessionId", session.getSession().getId())
//                .add("isTurn", "false")
//                .add("status", status.toString())
//                .add("enemyNickname", session.getUserDto().getName())
//                .add("enemyRating", session.getUserDto().getUserHero().getRating())
//                .add("enemyHealth", session.getUserDto().getUserHero().getHealth())
//                .add("enemyDamage", session.getUserDto().getUserHero().getDamageMultiplier())
//                .build();
//        return gameObject.toString();
//    }

    public enum gameStatus {
        START, PAUSE, END, CONNECTIONLOST, DAMAGE;

        @Override public String toString() {
            String s = super.toString();
            return s.toLowerCase();
        }
    }
}
