package de.jordan.bankkonto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public final class DatenbankManager {

    private static final String URL = "jdbc:h2:file:./data/bankkonto";

    private DatenbankManager() {

    }

    public static Connection verbindungOeffnen() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void  tabelleErstellen(Connection connection) throws SQLException {

        String sql ="""
                CREATE TABLE IF NOT EXISTS bankkonto (
                    kontonummer VARCHAR(30) PRIMARY KEY,
                    kontoinhaber VARCHAR(100) NOT NULL,
                    kontostand DECIMAL(15, 2) NOT NULL    
                )
                """;

        try(Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
                
    }
}
