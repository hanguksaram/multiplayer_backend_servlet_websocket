package app.services;

import app.dtos.UserDtoResponse;
import app.dtos.UserHeroDto;
import app.models.GameSession;
import app.repos.CharacterRepo;
import app.repos.CharacterRepoImpl;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.websocket.Session;
import java.util.Random;


public class GameMechanicsHandler {



    private static CharacterRepo charRepo;
    private static int minDamage = 3;
    static {
        try {
            charRepo = new CharacterRepoImpl(null);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static void difineDamageDealer(GameSession gameSession, Session session) {
        gameSession.setGameStatus(GameStatusNotifier.gameStatus.DAMAGE);
        boolean isKilled = false;
        UserDtoResponse damageDealer = gameSession.getSessionOne().getUserDto();;
        UserDtoResponse damageReceiver = gameSession.getSessionTwo().getUserDto();
        if (gameSession.getSessionOne().getSessionId().equals(session.getId())){

            isKilled = dealDamage(damageDealer.getUserHero(), damageReceiver.getUserHero());
            if (isKilled) {
                gameSession.setGameStatus(GameStatusNotifier.gameStatus.END);
                increaseHeroStats(damageDealer, damageReceiver);
            }
        }
        else {
            isKilled = dealDamage(damageReceiver.getUserHero(), damageDealer.getUserHero());
            if (isKilled) {
                gameSession.setGameStatus(GameStatusNotifier.gameStatus.END);
                increaseHeroStats(damageReceiver, damageDealer);
            }
        }

    }

    private static int getRandomDamageNumber(int maxDamage) {
        Random random = new Random();
        return random.nextInt((maxDamage - minDamage) + 1) + minDamage;
    }
    //if damageReceiver's healh decreased below zero method return true
    private static boolean dealDamage (UserHeroDto damageDealer, UserHeroDto damageReceiver) {
        damageDealer.setIsTurn(0);
        damageReceiver.setIsTurn(1);
        int damageValue = getRandomDamageNumber(damageDealer.getDamageMultiplier());
        int healthLeft = damageReceiver.getCurrentHealth() - damageValue;
        if (healthLeft <= 0 ) {
            return true;
        }
        damageReceiver.setCurrentHealth(healthLeft);
        damageReceiver.setHealthPercentage((int)(damageReceiver.getCurrentHealth() / (float)damageReceiver.getHealth() * 100));
        damageDealer.setDamageDealed(damageValue);
        damageReceiver.setDamageDealed(damageValue);
        return false;
    }
    public static int defineStartingPlayer(){
        Random random = new Random();
        return random.nextInt(1);
    }
    private static void increaseHeroStats(UserDtoResponse winner, UserDtoResponse looser) {
        winner.getUserHero().setRating(winner.getUserHero().getRating() + 1);
        winner.getUserHero().setHealth(winner.getUserHero().getHealth() + 1);
        winner.getUserHero().setDamageMultiplier(winner.getUserHero().getDamageMultiplier() + 1);
        looser.getUserHero().setRating(looser.getUserHero().getRating() - 1);
        looser.getUserHero().setHealth(looser.getUserHero().getHealth() + 1);
        looser.getUserHero().setDamageMultiplier(looser.getUserHero().getDamageMultiplier() + 1);
        charRepo.updateCharacter(winner);
        charRepo.updateCharacter(looser);

    }


}
