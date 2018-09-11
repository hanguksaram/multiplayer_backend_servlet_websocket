package app.dtos;



public class UserDtoRequest {
    private String Login;
    private String Password;

    public UserDtoRequest(String login, String password) {
        this.Login = login;
        this.Password = password;
    }
    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDtoRequest user = (UserDtoRequest) o;

        if (Login != null ? !Login.equals(user.Login) : user.Login != null) return false;
        return Password != null ? Password.equals(user.Password) : user.Password == null;
    }

    @Override
    public int hashCode() {
        int result = Login != null ? Login.hashCode() : 0;
        result = 31 * result + (Password != null ? Password.hashCode() : 0);
        return result;
    }
}
