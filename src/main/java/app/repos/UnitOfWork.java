package app.repos;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

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
    public static Connection getConnection() throws URISyntaxException, SQLException {
        //URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
        try{Class.forName("com.mysql.jdbc.Driver");}
        catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        URI dbUri = new URI("mysql://newuser:password@localhost:3306/test01");
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
