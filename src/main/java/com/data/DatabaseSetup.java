package com.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseSetup {

    private static Connection connectToDatabase() {
        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        System.out.println("DB_URL: " + dbUrl);
        System.out.println("DB_USER: " + user);
        System.out.println("DB_PASSWORD: " + password);
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(dbUrl, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void executeSqlScript(Connection conn, String filePath) throws SQLException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] sqlStatements = content.split(";\\s*\\n");

        try (Statement stmt = conn.createStatement()) {
            for (String statement : sqlStatements) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = connectToDatabase();
            if (conn != null) {
                executeSqlScript(conn, "src/main/java/com/data/database.sql");
                System.out.println("Database setup completed successfully.");
                conn.close();
            } else {
                System.err.println("Failed to connect to the database.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
