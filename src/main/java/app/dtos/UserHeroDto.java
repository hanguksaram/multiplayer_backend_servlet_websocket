package app.dtos;

public class UserHeroDto {
    private int rating;
    private int damageMultiplier;
    private int health;

    public UserHeroDto(int rating, int damageMultiplier, int health) {
        this.rating = rating;
        this.damageMultiplier = damageMultiplier;
        this.health = health;
    }

    public int getRating() {
        return rating;
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
