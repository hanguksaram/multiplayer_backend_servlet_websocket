package app.repos;

import app.Utils.Utils;
import app.dtos.UserDtoResponse;
import app.models.DbModel;
import app.models.User;
import app.models.UserHero;


import javax.sql.DataSource;
import java.sql.*;

public class CharacterRepoImpl implements BaseRepo<UserHero, UserDtoResponse> {

    @Override
    public DbModel<UserHero> getEntityById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserHero userHero = new UserHero();
        DbModel<UserHero> heroModel = new DbModel<>();
        double sqlDuration = 0;
        try {
            conn = UnitOfWork.getConnection();
            String sql = "select * from Characters where User_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                userHero.setId(resultSet.getInt("User_ID"));
                userHero.setRating(resultSet.getInt("Rating"));
                userHero.setDamageMultiplier(resultSet.getInt("DamageMultiplier"));
                userHero.setHealth(resultSet.getInt("Health"));
            }
            sqlDuration = getLastQueryDuration();
            heroModel.setEntity(userHero);
            heroModel.setSqlQueryDuration(sqlDuration);


        }
        catch (Exception ex) {
            ex.printStackTrace();
            heroModel.setSuccess(false);
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return heroModel;
        }

    }

    @Override
    public DbModel<UserHero> createEntityById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        DbModel<UserHero> heroModel = new DbModel<>();
        double sqlDuration = 0;
        try {
            conn = UnitOfWork.getConnection();
            String sql = ("insert into Characters " +
                    "values (?, ?, ?, ?)");
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, 1);//Raitng
            stmt.setInt(3, 10);//Damage
            stmt.setInt(4,100);//Health
            stmt.execute();
            sqlDuration = getLastQueryDuration();
            heroModel = this.getEntityById(id);
            heroModel.setSqlCount(heroModel.getSqlCount() + 1);
            heroModel.setSqlQueryDuration(heroModel.getSqlQueryDuration() + sqlDuration);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            heroModel.setSuccess(false);
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return heroModel;
        }
    }

    @Override
    public DbModel<UserHero> updateEntitybyDto(UserDtoResponse dtoModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        DbModel<UserHero> heroModel = new DbModel<>();
        double sqlDuration = 0;

        try {
            conn = UnitOfWork.getConnection();
            String sql = "update Characters " +
                    "set Rating = ? , DamageMultiplier = ? , Health = ? " +
                    "where User_ID =  ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, dtoModel.getUserHero().getRating());
            stmt.setInt(2, dtoModel.getUserHero().getDamageMultiplier());//Raitng
            stmt.setInt(3, dtoModel.getUserHero().getHealth());//Damage
            stmt.setInt(4, dtoModel.getId());//Health
            stmt.execute();
            sqlDuration = getLastQueryDuration();
            heroModel = this.getEntityById(dtoModel.getId());
            heroModel.setSqlCount(heroModel.getSqlCount() + 1);
            heroModel.setSqlQueryDuration(heroModel.getSqlQueryDuration() + sqlDuration);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            heroModel.setSuccess(false);

        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return heroModel;
        }
    }

    @Override
    public DbModel<UserHero> getEntityByName(String name) {
        return null;
    }

    @Override
    public DbModel<UserHero> createEntityByDto(UserDtoResponse dtoModel) {
        return null;
    }
}
