package com.data;

import com.model.Booking;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.time.LocalDate;

public class BookingTable {
	private Connection conn;

	public BookingTable() throws Exception {
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

	public void save(Booking b) throws SQLException {
		String sql = "INSERT INTO Bookings (BookingID, UserID, TripID, TripDate) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, b.getBookingID());
			statement.setString(2, b.getUserID());
			statement.setString(3, b.getTripID());
			statement.setDate(4, Date.valueOf(b.getTripDate()));
			statement.executeUpdate();
		}
	}

	public Booking findById(String bookingID) throws SQLException {
		String sql = "SELECT * FROM Bookings WHERE BookingID = ?";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, bookingID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return new Booking(rs.getString("BookingID"), rs.getString("UserID"), rs.getString("TripID"), rs.getDate("TripDate").toLocalDate());
			}
		}
		return null;
	}

	public void deleteBooking(String bookingID) throws SQLException {
		String sql = "DELETE FROM Bookings WHERE BookingID = ?";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, bookingID);
			statement.executeUpdate();
		}
	}

	public boolean isValid(String bookingID, String userID, String tripID, LocalDate tripDate) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Bookings WHERE BookingID = ? AND UserID = ? AND TripID = ? AND TripDate = ?";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, bookingID);
			statement.setString(2, userID);
			statement.setString(3, tripID);
			statement.setDate(4, Date.valueOf(tripDate));
			ResultSet rs = statement.executeQuery();
			rs.next();
			return rs.getInt(1) > 0;
		}
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
