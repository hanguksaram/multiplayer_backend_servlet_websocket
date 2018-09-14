package app.repos;

import app.dtos.UserDtoResponse;
import app.models.UserHero;

public interface CharacterRepo {
    public UserHero getCharacterByUserId(int userId);
    public UserHero createCharacter(int userId);
    public boolean updateCharacter(UserDtoResponse userDto);

}
