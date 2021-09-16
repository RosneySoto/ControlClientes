package datos;

import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/control_clientes?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456789";

    private static BasicDataSource dataSource;

    public static BasicDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(JDBC_URL);
            dataSource.setUsername(JDBC_USER);
            dataSource.setPassword(JDBC_PASSWORD);
            dataSource.setInitialSize(50);
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
