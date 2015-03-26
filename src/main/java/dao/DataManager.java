package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataManager {

    public static Connection getConnection() throws Exception {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        int port = dbUri.getPort();

        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath();

        //throw new Exception("username:" + username + ", password:" + password + ", DATABASE_URL: " + System.getenv("DATABASE_URL") + ", port:" + port);

        return DriverManager.getConnection(dbUrl, username, password);
    }

}
