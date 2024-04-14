package com.data;

import com.model.User;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class UserTable {
	private Connection conn;

	public UserTable() throws Exception {
		this.conn = getCon();
	}

	private Connection getCon() throws Exception {
		Dotenv dotenv = Dotenv.load();
		String dbUrl = dotenv.get("DB_URL");
		String user = dotenv.get("DB_USER");
		String password = dotenv.get("DB_PASSWORD");
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(dbUrl, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void save(User user) throws SQLException {
		String query = "INSERT INTO Users (Password, FirstName, LastName) VALUES (?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			ps.executeUpdate();

			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
				}
			}
		}
	}

	public User findById(Number id) throws SQLException {
		String query = "SELECT * FROM Users WHERE ID = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, id.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new User(rs.getString("Password"), rs.getString("FirstName"), rs.getString("LastName"));
			}
		}
		return null;
	}

	public void deleteUser(String id) throws SQLException {
		String query = "DELETE FROM Users WHERE ID = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, id);
			ps.executeUpdate();
		}
	}

	public boolean isValid(Number id, String password) throws SQLException {
		String query = "SELECT COUNT(*) FROM Users WHERE ID = ? AND Password = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, id.toString());
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		}
		return false;
	}

	public void disCon() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
