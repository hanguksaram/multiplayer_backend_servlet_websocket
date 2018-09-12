package app.models;

import javax.websocket.Session;

public class GameSession {
    private String gameSessionId;
    private UserSession sessionOne;
    private UserSession sessionTwo;

    public GameSession(String gameSessionId ,UserSession sessionOne, UserSession sessionTwo) {
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
}
