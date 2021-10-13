package classes;

import java.sql.Connection;

// Based off of tutorial from https://www.youtube.com/watch?v=dYt763QgaTg
public class ConnectionHelper {
    private Connection connection;
    String username, password, ip, port, database;

    public Connection getConnection(){
        return connection;
    }
}
