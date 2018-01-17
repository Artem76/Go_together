package app.service.databaseConnectionService;

import java.sql.ResultSet;

/**
 * Created by Олег on 08.11.2017.
 */
public interface DatabaseConnectionService {
    ResultSet getResultSetBySQLText(String query);
}
