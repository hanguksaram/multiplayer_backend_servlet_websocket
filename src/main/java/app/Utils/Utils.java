package app.Utils;

import app.models.DbModel;

import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {
    public static void profileSql(Statement stmt, PreparedStatement prStmt, String sql) throws SQLException {
        stmt.execute("SET PROFILING=1;");
        prStmt.execute();
        stmt.execute("SET PROFILING=0;");
    }
    public static ResultSet profileSqlSet(Statement stmt, PreparedStatement prStmt, String sql) throws SQLException {
        stmt.execute("SET PROFILING=1;");
        ResultSet resultSet = prStmt.executeQuery();
        stmt.execute("SET PROFILING=0;");
        return resultSet;
    }
    public static <T> void saveSqlLogs(HttpSession session, DbModel<T> model){
        session.setAttribute("sqlLog", String.format("Db: %d req, %f ms", model.getSqlCount(), model.getSqlQueryDuration()));
    };
}
