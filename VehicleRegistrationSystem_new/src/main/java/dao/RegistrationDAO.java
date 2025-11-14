package dao;

import model.Registration;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class RegistrationDAO {
	private static final String INSERT = "INSERT INTO registration (registration_id, vehicle_id, registration_date, expiry_date) VALUES (registration_seq.NEXTVAL, ?, ?, ?)";
	private static final String SELECT_VEH = "SELECT * FROM registration WHERE vehicle_id = ?";

	public int addRegistration(Registration r) {
	    int generatedId = -1;
	    String sql = "SELECT registration_seq.CURRVAL FROM dual";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(INSERT);
	         Statement stmt = conn.createStatement()) {

	        ps.setInt(1, r.getVehicleId());
	        ps.setDate(2, r.getRegistrationDate());
	        ps.setDate(3, r.getExpiryDate());
	        ps.executeUpdate();

	        try (ResultSet rs = stmt.executeQuery(sql)) {
	            if (rs.next()) generatedId = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return generatedId;
	}

    public List<Registration> getRegistrationsByVehicleId(int vehicleId) {
        List<Registration> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_VEH)) {
            ps.setInt(1, vehicleId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    private Registration map(ResultSet rs) throws SQLException {
        return new Registration(rs.getInt("registration_id"), rs.getInt("vehicle_id"),
                rs.getDate("registration_date"), rs.getDate("expiry_date"));
    }
}
