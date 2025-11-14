package servlet;

import dao.CustomerDAO;
import dao.VehicleDAO;
import dao.RegistrationDAO;
import model.Customer;
import model.Vehicle;
import model.Registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handles vehicle registration form submission.
 * DEBUG version: prints progress + IDs to server console.
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // --- Read form fields ---
            String name  = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String brand = request.getParameter("brand");   // input name="brand" in form
            String model = request.getParameter("model");
            String yearStr = request.getParameter("year");

            System.out.println("[RegisterServlet] Received form -> " +
                    "name=" + name + ", email=" + email + ", phone=" + phone +
                    ", brand=" + brand + ", model=" + model + ", year=" + yearStr);

            int year = Integer.parseInt(yearStr.trim());

            // --- Insert Customer ---
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);

            CustomerDAO customerDAO = new CustomerDAO();
            int customerId = customerDAO.addCustomer(customer);
            System.out.println("[RegisterServlet] addCustomer returned ID=" + customerId);

            if (customerId <= 0) {
                showError(out, "Customer insert failed.");
                return;
            }

            // --- Insert Vehicle (IMPORTANT: set customerId!) ---
            Vehicle vehicle = new Vehicle();
            vehicle.setCustomerId(customerId);   // <<< YOU WERE NOT DOING THIS
            vehicle.setBrand(brand);
            vehicle.setModel(model);
            vehicle.setYear(year);

            VehicleDAO vehicleDAO = new VehicleDAO();
            int vehicleId = vehicleDAO.addVehicle(vehicle);
            System.out.println("[RegisterServlet] addVehicle returned ID=" + vehicleId);

            if (vehicleId <= 0) {
                showError(out, "Vehicle insert failed.");
                return;
            }

            // --- Insert Registration ---
            Registration registration = new Registration();
            registration.setVehicleId(vehicleId);
            // If your Registration table has customer_id col, uncomment:
            // registration.setCustomerId(customerId);
            // If it has registration_date / expiry_date you must set them in DAO.

            RegistrationDAO registrationDAO = new RegistrationDAO();
            int regId = registrationDAO.addRegistration(registration);
            System.out.println("[RegisterServlet] addRegistration returned ID=" + regId);

            if (regId <= 0) {
                System.out.println("[RegisterServlet] WARNING: registration insert not confirmed.");
            }

            // --- Success HTML response ---
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'><title>Registration Successful</title>");
            out.println("<style>");
            out.println("body{font-family:Arial,sans-serif;background:#f2f2f2;text-align:center;padding-top:50px;}");
            out.println(".container{background:#fff;padding:20px 30px;border-radius:8px;display:inline-block;box-shadow:0 0 10px rgba(0,0,0,.1);}");
            out.println("h2{color:green;}p{font-size:18px;}a{display:inline-block;margin-top:10px;text-decoration:none;color:#007BFF;font-weight:bold;}a:hover{text-decoration:underline;}");
            out.println("</style></head><body><div class='container'>");
            out.println("<h2>Registration Successful!</h2>");
            out.println("<p><b>Customer ID:</b> " + customerId + "</p>");
            out.println("<p><b>Vehicle ID:</b> " + vehicleId + "</p>");
            out.println("<a href='register.html'>Register Another Vehicle</a><br>");
            out.println("<a href='ListVehiclesServlet'>View All Vehicles</a>");
            out.println("</div></body></html>");

        } catch (Exception e) {
            e.printStackTrace(); // console
            showError(out, "Error: " + e.getMessage());
        }
    }

    private void showError(PrintWriter out, String msg) {
        out.println("<h2 style='color:red;'>" + msg + "</h2>");
        out.println("<a href='register.html'>Back to Registration</a>");
    }
}
