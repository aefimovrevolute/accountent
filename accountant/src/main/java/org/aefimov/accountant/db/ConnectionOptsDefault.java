package org.aefimov.accountant.db;


import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class ConnectionOptsDefault implements ConnectionOpts {

    private static final String CONNECTION_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.
                    getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
