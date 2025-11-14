package test;

import dao.CustomerDAO;
import dao.VehicleDAO;
import dao.RegistrationDAO;
import model.Customer;
import model.Vehicle;
import model.Registration;
import util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public class OracleTestConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connected to Oracle.");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        CustomerDAO cdao = new CustomerDAO();
        VehicleDAO vdao = new VehicleDAO();
        RegistrationDAO rdao = new RegistrationDAO();

        Customer c = new Customer("Test User", "test@example.com", "1234567890");
        int cid = cdao.addCustomer(c);
        System.out.println("Customer ID: " + cid);

        Vehicle v = new Vehicle(cid, "Honda", "City", 2023);
        int vid = vdao.addVehicle(v);
        System.out.println("Vehicle ID: " + vid);

        Registration r = new Registration(vid, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusYears(1)));
        int rid = rdao.addRegistration(r);
        System.out.println("Registration ID: " + rid);
    }
}
