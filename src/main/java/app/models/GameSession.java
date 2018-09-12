package app.models;

import javax.websocket.Session;

public class GameSession {
    private UserSession sessionOne;
    private UserSession sessionTwo;

    public GameSession(UserSession sessionOne, UserSession sessionTwo) {
        this.sessionOne = sessionOne;
        this.sessionTwo = sessionTwo;
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
