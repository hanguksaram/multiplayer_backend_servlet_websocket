package app.repos;

import app.dtos.UserDtoResponse;
import app.models.UserHero;


import javax.sql.DataSource;
import java.sql.*;

public class CharacterRepoImpl implements CharacterRepo {

    private DataSource dataSource;

    public CharacterRepoImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    public UserHero getCharacterByUserId(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        UserHero userHero = new UserHero();
        try {
            conn = UnitOfWork.getConnection();
            //conn = dataSource.getConnection();
            String sql = "select * from Characters where User_ID = " + "'" + userId + "'";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                userHero.setId(resultSet.getInt("User_ID"));
                userHero.setRating(resultSet.getInt("Rating"));
                userHero.setDamageMultiplier(resultSet.getInt("DamageMultiplier"));
                userHero.setHealth(resultSet.getInt("Health"));
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
        }
        return userHero.getId() != 0  ? userHero : null;
    }


    public UserHero createCharacter(int userId) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserHero userHero = null;

        try {
            conn = UnitOfWork.getConnection();
            //conn = dataSource.getConnection();
            String sql = "insert into Characters " +
                    "values (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, 1);//Raitng
            stmt.setInt(3, 10);//Damage
            stmt.setInt(4,100);//Health
            stmt.execute();
            userHero = getCharacterByUserId(userId);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return userHero;
        }

    }


    public boolean updateCharacter(UserDtoResponse userDtoResponse) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        boolean updated = true;


        try {

            //conn = dataSource.getConnection();
            conn = UnitOfWork.getConnection();
            String sql = "update Characters " +
                    "set Rating = ? , DamageMultiplier = ? , Health = ? " +
                    "where User_ID =  ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userDtoResponse.getUserHero().getRating());
            stmt.setInt(2, userDtoResponse.getUserHero().getDamageMultiplier());//Raitng
            stmt.setInt(3, userDtoResponse.getUserHero().getHealth());//Damage
            stmt.setInt(4,userDtoResponse.getId());//Health
            stmt.execute();



        }
        catch (Exception ex) {
            ex.printStackTrace();
            updated = false;
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return updated;
        }
    }
}
    //INSERT INTO `test01`.`Characters` (`User_ID`, `Rating`, `DamageMultiplier`) VALUES ('12', '1', '1');