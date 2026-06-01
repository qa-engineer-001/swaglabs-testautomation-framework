package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBReader {

    private static final Logger logger = LogManager.getLogger(DBReader.class);
    private static Connection connection;

    public static void connect() throws Exception {
        String dbUrl  = ConfigReader.get("db.url");
        String dbUser = ConfigReader.get("db.username");
        String dbPass = ConfigReader.get("db.password");
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        logger.info("DB Connected Successfully");
    }

    public static Object[][] getUserCredentials() throws Exception {
        connect();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT username, password FROM users");

        List<Object[]> data = new ArrayList<>();
        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            data.add(new Object[]{username, password});
        }

        connection.close();
        return data.toArray(new Object[0][]);
    }
}
