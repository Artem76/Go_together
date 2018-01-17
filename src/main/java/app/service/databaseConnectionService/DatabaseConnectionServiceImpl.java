package app.service.databaseConnectionService;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Олег on 08.11.2017.
 */

@Service
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

    @Autowired
    EntityManager entityManager;


    @Override
    @Transactional
    public ResultSet getResultSetBySQLText(String query) {
        ResultSet rs = null;
        Connection conn = null;
        Statement statement = null;
        Session session = entityManager.unwrap(Session.class);
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
        ConnectionProvider cp = sfi.getConnectionProvider();
        try {
            conn = cp.getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            if (rs != null) try {
                rs.close();
            } catch (SQLException logOrIgnore) {
            }
            if (statement != null) try {
                statement.close();
            } catch (SQLException logOrIgnore) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException logOrIgnore) {
            }
        }
        return rs;
    }
}
