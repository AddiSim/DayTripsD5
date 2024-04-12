package com.data;

import com.model.Booking;
import com.model.Trip;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	public List<Booking> findByUserId(String userID) throws SQLException {
		List<Booking> bookings = new ArrayList<>();
		String sql = "SELECT * FROM Bookings WHERE UserID = ?";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, userID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				bookings.add(new Booking(rs.getString("BookingID"), rs.getString("UserID"), rs.getString("TripID"), rs.getDate("TripDate").toLocalDate()));
			}
		}
		return bookings;
	}

	public List<Booking> findAll() throws SQLException {
		List<Booking> bookings = new ArrayList<>();
		String sql = "SELECT * FROM Bookings";
		try (Statement statement = conn.createStatement()) {
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				bookings.add(new Booking(rs.getString("BookingID"), rs.getString("UserID"), rs.getString("TripID"), rs.getDate("TripDate").toLocalDate()));
			}
		}
		return bookings;
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

	public List<Booking> findByTripId(String tripId) throws SQLException {
		List<Booking> bookings = new ArrayList<>();
		String sql = "SELECT * FROM Bookings WHERE TripID = ?";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, tripId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				bookings.add(new Booking(rs.getString("BookingID"), rs.getString("UserID"), rs.getString("TripID"), rs.getDate("TripDate").toLocalDate()));
			}
		}
		return bookings;
	}

	public List<Trip> findByTripName(String tripName) throws SQLException {
		String query = "SELECT * FROM Trips WHERE Location = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, tripName);
			ResultSet rs = ps.executeQuery();
			List<Trip> trips = new ArrayList<>();
			while (rs.next()) {
				String tripID = rs.getString("TripID");
				LocalDate tripDate = rs.getDate("TripDate").toLocalDate();
				String tripName1 = rs.getString("TripName");
				String location = rs.getString("Location");
				int price = rs.getInt("Price");
				Trip trip = new Trip(tripID, tripDate, tripName1, location, price);
				trips.add(trip);
			}
			return trips;
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
