package app.repos;

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
            conn = dataSource.getConnection();
            String sql = "select * from Characters where User_ID = " + "'" + userId + "'";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                userHero.setId(resultSet.getInt("User_ID"));
                userHero.setRating(resultSet.getInt("Rating"));
                userHero.setDamageMultiplier(resultSet.getInt("DamageMultiplier"));
                userHero.setHealth(resultSet.getInt("health"));
            }

        }
        catch (SQLException ex) {
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
            conn = dataSource.getConnection();
            String sql = "insert into Characters " +
                    "values (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, 1);//Raitng
            stmt.setInt(3, 10);//Damage
            stmt.execute();
            userHero = getCharacterByUserId(userId);

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return userHero;
        }

    }


    public UserHero updateCharacter(boolean winOrLoose, int userId) {
        return null;
    }
}
    //INSERT INTO `test01`.`Characters` (`User_ID`, `Rating`, `DamageMultiplier`) VALUES ('12', '1', '1');