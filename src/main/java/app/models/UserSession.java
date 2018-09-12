package app.models;

import app.dtos.UserDtoResponse;

import javax.websocket.Session;

public class UserSession {
    private String sessionId;
    private Session session;
    private UserDtoResponse userDto;

    public UserSession(Session session, UserDtoResponse userDto) {
        this.session = session;
        this.sessionId = this.session.getId();
        this.userDto = userDto;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public UserDtoResponse getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDtoResponse userDto) {
        this.userDto = userDto;
    }
}
