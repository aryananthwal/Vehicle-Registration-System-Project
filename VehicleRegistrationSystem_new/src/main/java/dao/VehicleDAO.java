package dao;

import model.Vehicle;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    // SQL for inserting vehicle
    private static final String INSERT_SQL =
        "INSERT INTO vehicle (vehicle_id, customer_id, brand, model, year) " +
        "VALUES (vehicle_seq.NEXTVAL, ?, ?, ?, ?)";

    // SQL for fetching all vehicles
    private static final String SELECT_ALL_SQL =
        "SELECT vehicle_id, customer_id, brand, model, year FROM vehicle ORDER BY vehicle_id";

    /**
     * Add new vehicle
     */
    public int addVehicle(Vehicle v) {
        int id = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
             Statement getIdStmt = conn.createStatement()) {

            ps.setInt(1, v.getCustomerId());
            ps.setString(2, v.getBrand());
            ps.setString(3, v.getModel());
            ps.setInt(4, v.getYear());

            ps.executeUpdate();

            // Get generated ID from sequence
            try (ResultSet rs = getIdStmt.executeQuery("SELECT vehicle_seq.CURRVAL FROM dual")) {
                if (rs.next()) {
                    id = rs.getInt(1);
                    v.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Get all vehicles
     */
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("vehicle_id"));
                v.setCustomerId(rs.getInt("customer_id"));
                v.setBrand(rs.getString("brand"));
                v.setModel(rs.getString("model"));
                v.setYear(rs.getInt("year"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get vehicle by ID
     */
    public Vehicle getVehicleById(int id) {
        Vehicle vehicle = null;
        String sql = "SELECT vehicle_id, customer_id, brand, model, year FROM vehicle WHERE vehicle_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                vehicle = new Vehicle();
                vehicle.setId(rs.getInt("vehicle_id"));
                vehicle.setCustomerId(rs.getInt("customer_id"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setYear(rs.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }

    /**
     * Get vehicles by customer ID
     */
    public List<Vehicle> getVehiclesByCustomerId(int customerId) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT vehicle_id, brand, model, year FROM vehicle WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("vehicle_id"));
                v.setBrand(rs.getString("brand"));
                v.setModel(rs.getString("model"));
                v.setYear(rs.getInt("year"));
                vehicles.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    /**
     * Update vehicle details
     */
    public boolean updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicle SET brand = ?, model = ?, year = ? WHERE vehicle_id = ?";
        boolean rowUpdated = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicle.getBrand());
            ps.setString(2, vehicle.getModel());
            ps.setInt(3, vehicle.getYear());
            ps.setInt(4, vehicle.getId());

            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    /**
     * Delete vehicle by ID
     */
    public boolean deleteVehicle(int vehicleId) {
        String deleteRegsSql = "DELETE FROM registration WHERE vehicle_id = ?";
        String deleteVehicleSql = "DELETE FROM vehicle WHERE vehicle_id = ?";
        boolean success = false;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // start transaction

            // Delete registrations first (due to foreign key constraint)
            try (PreparedStatement ps1 = conn.prepareStatement(deleteRegsSql)) {
                ps1.setInt(1, vehicleId);
                ps1.executeUpdate();
            }

            // Delete the vehicle
            int rows;
            try (PreparedStatement ps2 = conn.prepareStatement(deleteVehicleSql)) {
                ps2.setInt(1, vehicleId);
                rows = ps2.executeUpdate();
            }

            conn.commit();
            success = rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
