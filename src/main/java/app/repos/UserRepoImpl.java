package app.repos;

import app.Utils.Utils;
import app.dtos.UserDtoRequest;
import app.models.DbModel;
import app.models.User;


import java.sql.*;


public class UserRepoImpl implements BaseRepo<User, UserDtoRequest> {


    @Override
    public DbModel<User> getEntityById(int id) {
        return null;
    }

    @Override
    public DbModel<User> createEntityById(int id) {
        return null;
    }

    @Override
    public DbModel<User> updateEntitybyDto(UserDtoRequest dtoModel) {
        return null;
    }

    @Override
    public DbModel<User> getEntityByName(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        User user = new User();
        DbModel<User> userModel = new DbModel<>();
        double sqlDuration = 0;
        try {
            conn = UnitOfWork.getConnection();
            String sql = "select * from Users where Login = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            resultSet = Utils.profileSqlSet(conn.createStatement(), stmt, sql);
            while (resultSet.next()) {
                user.setId(resultSet.getInt("User_ID"));
                user.setLogin(resultSet.getString("Login"));
                user.setPassword(resultSet.getString("Password"));
            }
            sqlDuration = getLastQueryDuration();
            userModel.setEntity(user);
            userModel.setSqlQueryDuration(sqlDuration);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            userModel.setSuccess(false);
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return userModel;
        }

    }

    @Override
    public DbModel<User> createEntityByDto(UserDtoRequest dtoModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        DbModel<User> userModel = new DbModel<>();
        double sqlDuration = 0;
        try {
            conn = UnitOfWork.getConnection();
            String sql = "insert into Users (Login, Password)\n" +
                    "values (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dtoModel.getLogin());
            stmt.setString(2, dtoModel.getPassword());
            Utils.profileSql(conn.createStatement(), stmt, sql);
            sqlDuration = getLastQueryDuration();
            userModel = this.getEntityByName(dtoModel.getLogin());
            userModel.setSqlCount(userModel.getSqlCount() + 1);
            userModel.setSqlQueryDuration(userModel.getSqlQueryDuration() + sqlDuration);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            userModel.setSuccess(false);
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return userModel;
        }
    }

}
