package app.models;

import javax.websocket.Session;

public class GameSession {
    private Session sessionOne;
    private Session sessionTwo;

    public GameSession(Session sessionOne, Session sessionTwo) {
        this.sessionOne = sessionOne;
        this.sessionTwo = sessionTwo;
    }

    public Session getSessionOne() {
        return sessionOne;
    }

    public void setSessionOne(Session sessionOne) {
        this.sessionOne = sessionOne;
    }

    public Session getSessionTwo() {
        return sessionTwo;
    }

    public void setSessionTwo(Session sessionTwo) {
        this.sessionTwo = sessionTwo;
    }
}
