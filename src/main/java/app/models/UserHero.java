package app.models;

public class UserHero {
    private int id;
    private int rating;
    private int damageMultiplier;
    private int health;

    public UserHero(){};
    public UserHero(int rating, int damageMultiplier, int id, int health) {
        this.id = id;
        this.damageMultiplier = damageMultiplier;
        this.rating = rating;
        this.health = health;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHero userHero = (UserHero) o;

        if (id != userHero.id) return false;
        if (rating != userHero.rating) return false;
        if (damageMultiplier != userHero.damageMultiplier) return false;
        return health == userHero.health;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + rating;
        result = 31 * result + damageMultiplier;
        result = 31 * result + health;
        return result;
    }
}
