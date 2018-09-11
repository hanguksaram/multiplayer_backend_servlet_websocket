package app.repos;

import app.dtos.UserDtoRequest;
import app.models.User;
import javax.sql.DataSource;
import java.sql.*;


public class UserRepoImpl implements UserRepo {

    private DataSource dataSource;

    public UserRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User getUserByName(String name) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        User user = new User();
        try {

            conn = dataSource.getConnection();
            String sql = "select * from Users where Login = " + "'" + name + "'";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                user.setId(resultSet.getInt("User_ID"));
                user.setLogin(resultSet.getString("Login"));
                user.setPassword(resultSet.getString("Password"));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
        }
        return user.getLogin() != ""  ? user : null;
    }

    public User createUser(UserDtoRequest userDto) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            conn = dataSource.getConnection();
            String sql = "insert into Users (Login, Password)\n" +
                    "values (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userDto.getLogin());
            stmt.setString(2, userDto.getPassword());
            stmt.execute();
            user = this.getUserByName(userDto.getLogin());

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return user;
        }

    }

}
