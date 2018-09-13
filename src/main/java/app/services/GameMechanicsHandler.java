package app.services;

import app.dtos.UserHeroDto;
import app.models.GameSession;

import javax.websocket.Session;
import java.util.Random;


public class GameMechanicsHandler {
    private static int minDamage = 5;
    public static void difineDamageDealer(GameSession gameSession, Session session) {
        if (gameSession.getSessionOne().getSessionId().equals(session.getId())){
            dealDamage(gameSession.getSessionTwo().getUserDto().getUserHero(), gameSession.getSessionOne().getUserDto().getUserHero());
        }
        else {
            dealDamage(gameSession.getSessionTwo().getUserDto().getUserHero(), gameSession.getSessionTwo().getUserDto().getUserHero());
        }

    }

    private static int getRandomDamageNumber(int maxDamage) {
        Random random = new Random();
        return random.nextInt((maxDamage - minDamage) + 1) + minDamage;
    }
    private static void dealDamage (UserHeroDto damageDealer, UserHeroDto damageReceiver) {
        int damageValue = getRandomDamageNumber(damageDealer.getDamageMultiplier());
        damageReceiver.setCurrentHealth(damageReceiver.getCurrentHealth() - damageValue);
        damageReceiver.setHealthPercentage((int)(damageReceiver.getCurrentHealth() / (float)damageReceiver.getHealth() * 100));
    }


}
