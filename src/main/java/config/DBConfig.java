package config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() throws SQLException {
        String host = dotenv.get("DB_HOST", "localhost");
        String port = dotenv.get("DB_PORT", "5432");
        String db   = dotenv.get("DB_NAME");
        String user = dotenv.get("DB_USER");
        String pass = dotenv.get("DB_PASSWORD");

        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, db);

        return DriverManager.getConnection(url, user, pass);
    }
}
