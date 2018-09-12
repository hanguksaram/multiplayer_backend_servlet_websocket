package app.services;

import app.dtos.UserHeroDto;
import app.models.GameSession;
import app.models.UserSession;

import javax.websocket.Session;
import java.util.Random;


public class GameMechanicsHandler {
    private static int minDamage = 5;
    public static void calculateDamage (GameSession gameSession, Session session) {
        if (gameSession.getSessionTwo().getSessionId().equals(session)){
            dealDamage(gameSession.getSessionTwo().getUserDto().getUserHero(), gameSession.getSessionOne().getUserDto().getUserHero());
        }
        else {
            dealDamage(gameSession.getSessionOne().getUserDto().getUserHero(), gameSession.getSessionTwo().getUserDto().getUserHero());
        }

    }

    private static int randomNumberInRange(int maxDamage) {
        Random random = new Random();
        return random.nextInt((maxDamage - minDamage) + 1) + minDamage;
    }
    private static void dealDamage (UserHeroDto damageDealer, UserHeroDto damageReceiver) {
        int damageValue = randomNumberInRange(damageDealer.getDamageMultiplier());
        damageReceiver.setHealth(damageReceiver.getHealth() - damageValue);
    }


}
