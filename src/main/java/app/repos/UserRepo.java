package app.repos;

import app.dtos.UserDtoRequest;
import app.models.User;

public interface UserRepo {
    public User getUserByName(String name);
    public User createUser(UserDtoRequest userDto);
}
