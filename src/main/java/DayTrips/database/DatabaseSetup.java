package DayTrips.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    private static Connection connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:database.db");
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
                executeSqlScript(conn, "src/main/java/com/database/database.sql");
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
