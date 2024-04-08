package com.data;

import com.model.Trip;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class TripTable{
	private Connection conn;

	public TripTable() throws Exception {
		getCon();
	}

	public Connection getCon() throws Exception {
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

	public void save(Trip trip) throws SQLException {
		String query = "INSERT INTO Trips (TripID, Location, TripDate, Price) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, trip.getTripID());
			ps.setString(2, trip.getLocation());
			ps.setDate(3, java.sql.Date.valueOf(trip.getTripDate()));
			ps.setInt(4, trip.getPrice());
			ps.executeUpdate();
		}
	}

	public Trip findById(String tripID) throws SQLException {
		String query = "SELECT * FROM Trips WHERE TripID = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, tripID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Trip(rs.getString("TripID"), rs.getString("Location"), rs.getDate("TripDate").toLocalDate(), rs.getInt("Price"));
			}
		}
		return null;
	}

	public void deleteTrip(String tripID) throws SQLException {
		String query = "DELETE FROM Trips WHERE TripID = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, tripID);
			ps.executeUpdate();
		}
	}

	public void update(Trip trip) throws SQLException {
		String query = "UPDATE Trips SET Location = ?, TripDate = ?, Price = ? WHERE TripID = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, trip.getLocation());
			ps.setDate(2, java.sql.Date.valueOf(trip.getTripDate()));
			ps.setInt(3, trip.getPrice());
			ps.setString(4, trip.getTripID());
			ps.executeUpdate();
		}
	}

	public boolean isValid(Trip trip) throws SQLException {
		String query = "SELECT COUNT(*) FROM Trips WHERE TripID = ? AND Location = ? AND TripDate = ? AND Price = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, trip.getTripID());
			ps.setString(2, trip.getLocation());
			ps.setDate(3, java.sql.Date.valueOf(trip.getTripDate()));
			ps.setInt(4, trip.getPrice());
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

	public void close() {
		disCon();
	}
}
