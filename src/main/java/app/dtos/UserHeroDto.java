package app.dtos;

public class UserHeroDto {
    private int rating;
    private int damageMultiplier;
    private int health;
    private int healthPercentage = 100;
    private int currentHealth;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    private int sessionId;

    public UserHeroDto(int rating, int damageMultiplier, int health) {
        this.rating = rating;
        this.damageMultiplier = damageMultiplier;
        this.health = health;
        this.currentHealth = health;

    }

    public int getRating() {
        return rating;
    }

    public int getHealthPercentage() {
        return healthPercentage;
    }

    public void setHealthPercentage(int healthPercentage) {
        this.healthPercentage = healthPercentage;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(int damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


}
