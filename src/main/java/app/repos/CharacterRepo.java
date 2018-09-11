package app.repos;

import app.models.UserHero;

public interface CharacterRepo {
    public UserHero getCharacterByUserId(int userId);
    public UserHero createCharacter(int userId);
    public UserHero updateCharacter(boolean winOrLoose, int userId);
}
