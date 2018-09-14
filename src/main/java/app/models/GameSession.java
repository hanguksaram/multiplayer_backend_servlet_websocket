package app.models;

import app.services.GameStatusNotifier;

public class GameSession {
    private String gameSessionId;
    private UserSession sessionOne;
    private UserSession sessionTwo;
    private GameStatusNotifier.gameStatus gameStatus;

    public GameSession(String gameSessionId ,UserSession sessionOne, UserSession sessionTwo) {
        this.gameStatus = GameStatusNotifier.gameStatus.START;
        this.gameSessionId = gameSessionId;
        this.sessionOne = sessionOne;
        this.sessionTwo = sessionTwo;
    }

    public String getGameSessionId() {
        return gameSessionId;
    }

    public void setGameSessionId(String gameSessionId) {
        this.gameSessionId = gameSessionId;
    }

    public UserSession getSessionOne() {
        return sessionOne;
    }

    public void setSessionOne(UserSession sessionOne) {
        this.sessionOne = sessionOne;
    }

    public UserSession getSessionTwo() {
        return sessionTwo;
    }

    public void setSessionTwo(UserSession sessionTwo) {
        this.sessionTwo = sessionTwo;
    }

    public GameStatusNotifier.gameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatusNotifier.gameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
