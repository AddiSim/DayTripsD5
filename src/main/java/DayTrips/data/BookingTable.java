package DayTrips.data;

import DayTrips.model.Booking;
import DayTrips.model.Trip;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BookingTable {
	private Connection conn;

	public BookingTable() throws Exception {
		this.conn = getCon();
	}

	public Connection getCon() throws Exception {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void createBooking(Booking b) throws SQLException {
		String sql = "INSERT INTO Bookings (UserID, TripID, TripDate) VALUES (?, ?, ?)";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setInt(1, b.getUserID());
			statement.setInt(2, b.getTripID());
			statement.setString(3, b.getTripDate().toString());
			statement.executeUpdate();
		}
	}

	public Booking findById(String bookingID) throws SQLException {
		String sql = "SELECT * FROM Bookings WHERE BookingID = ?";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, bookingID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				long timestamp = rs.getLong("TripDate");
				LocalDate tripDate = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
				int userId = rs.getInt("UserID");
				int tripId = rs.getInt("TripID");
				return new Booking(userId, tripId, tripDate);
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
				bookings.add(new Booking(rs.getInt("UserID"), rs.getInt("TripID"), rs.getDate("TripDate").toLocalDate()));
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
				bookings.add(new Booking(rs.getInt("UserID"), rs.getInt("TripID"), rs.getDate("TripDate").toLocalDate()));
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

	public boolean isValid(String userID, String tripID, LocalDate tripDate) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Bookings WHERE UserID = ? AND TripID = ? AND TripDate = ?";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, userID);
			statement.setString(2, tripID);
			statement.setDate(3, Date.valueOf(tripDate));
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
				bookings.add(new Booking(rs.getInt("UserID"), rs.getInt("TripID"), rs.getDate("TripDate").toLocalDate()));
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
				Integer tripID = rs.getInt("TripID");
				LocalDate tripDate = rs.getDate("TripDate").toLocalDate();
				String tripName1 = rs.getString("TripName");
				String location = rs.getString("Location");
				int price = rs.getInt("Price");
				Trip trip = new Trip(tripDate, tripName1, location, price);
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
