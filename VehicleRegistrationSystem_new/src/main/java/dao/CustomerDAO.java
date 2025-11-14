package dao;

import model.Customer;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class CustomerDAO {
	private static final String INSERT = "INSERT INTO customer (customer_id, name, email, phone) VALUES (customer_seq.NEXTVAL, ?, ?, ?)";
	private static final String SELECT_EMAIL = "SELECT * FROM customer WHERE email = ?";
    private static final String SELECT_ID = "SELECT * FROM customer WHERE customer_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM customer";

    public int addCustomer(Customer c) {
        int generatedId = -1;
        String sql = "SELECT customer_seq.CURRVAL FROM dual";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT);
             Statement stmt = conn.createStatement()) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhone());
            ps.executeUpdate();

            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) generatedId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedId;
    }


    public Customer getCustomerByEmail(String email) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    private Customer map(ResultSet rs) throws SQLException {
        return new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"));
    }
}
