package app.repos;

import app.models.DbModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface BaseRepo<T, K> {
    DbModel<T> getEntityById(int id);
    DbModel<T> createEntityById(int id);
    DbModel<T> updateEntitybyDto(K dtoModel);
    DbModel<T> getEntityByName(String name);
    DbModel<T> createEntityByDto(K dtoModel);
    default double getLastQueryDuration(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        double queryDuration = 0;
        try {
            //так и не смог понять почему резалтсет пустой, простой екзекут скля на бд серве отрабатывает как должен
            conn = UnitOfWork.getConnection();
            String sql = "select QUERY_ID, sum(duration) as duration from INFORMATION_SCHEMA.PROFILING\n" +
                    "group by query_id\n" +
                    "order by query_id desc\n" +
                    "limit 1";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                    queryDuration = (resultSet.getDouble("duration"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            UnitOfWork.close(conn, stmt, resultSet);
            return queryDuration;
        }
    }
}
