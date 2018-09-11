package app.repos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UnitOfWork {

    public static void close(Connection conn, Statement stmt, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
