package app.dtos;

import app.services.GameStatusNotifier;

public class StatusGameMessageDto {
    private String gameId = "";
    private String sessionId = "";
    private int isTurn = 0;
    private GameStatusNotifier.gameStatus status = GameStatusNotifier.gameStatus.START;
    private String enemyNickname  ="";
    private int enemyRating;
    private int enemyHealth;
    private int enemyDamage;

    public StatusGameMessageDto(String gameId, String sessionId, int isTurn, GameStatusNotifier.gameStatus status, String enemyNickname, int enemyRating, int enemyHealth, int enemyDamage) {
        this.gameId = gameId;
        this.sessionId = sessionId;
        this.isTurn = isTurn;
        this.status = status;
        this.enemyNickname = enemyNickname;
        this.enemyRating = enemyRating;
        this.enemyHealth = enemyHealth;
        this.enemyDamage = enemyDamage;
    }

    public StatusGameMessageDto(GameStatusNotifier.gameStatus status) {
        this.status = status;
    }
}
