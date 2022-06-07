package managers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public final class DBManager {
    private static volatile DBManager instance;
    private String url;
    private String user;
    private String password;

    {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = System.getProperties();
            prop.load(inputStream);
            url = prop.getProperty("url_address");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
            System.out.println("База данных сконфигурирована с db.properties.");
            Tunnel tunnel = new Tunnel("se.ifmo.ru", prop.getProperty("user"), prop.getProperty("password"),
                    2222, "pg", 8594, 5432);
            tunnel.connect();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private DBManager() {
        try (Connection testConnection = DriverManager.getConnection(url, user, password);
             ResultSet testRequest = testConnection.createStatement().executeQuery("SELECT version()")) {
            System.out.println("Идёт установка связи с БД.");
            while (testRequest.next())
                System.out.println("Связь с БД установлена." + " Версия: " + testRequest.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static DBManager getInstance() {
        DBManager instance2 = instance;
        if (instance2 == null) {
            synchronized (DBManager.class) {
                instance2 = instance;
                if (instance2 == null) instance = new DBManager();
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}