package servlet;

import dao.VehicleDAO;
import model.Vehicle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/SearchVehiclesServlet")
public class SearchVehiclesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String customerIdStr = request.getParameter("customerId");
        if (customerIdStr == null || customerIdStr.isEmpty()) {
            out.println("<h3 style='color:red;'>Customer ID is required!</h3>");
            return;
        }

        int customerId = Integer.parseInt(customerIdStr);

        try {
            VehicleDAO vehicleDAO = new VehicleDAO();
            List<Vehicle> vehicles = vehicleDAO.getVehiclesByCustomerId(customerId);

            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Search Results</title>");
            out.println("<style>");
            out.println("body { font-family: Arial; background-color: #f8f9fa; padding: 20px; }");
            out.println("table { width: 80%; margin: auto; border-collapse: collapse; background: #fff; }");
            out.println("th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }");
            out.println("th { background-color: #007BFF; color: white; }");
            out.println("a { display: block; text-align: center; margin-top: 15px; text-decoration: none; color: #007BFF; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<h2 style='text-align:center;'>Vehicles for Customer ID: " + customerId + "</h2>");

            if (vehicles.isEmpty()) {
                out.println("<p style='text-align:center;'>No vehicles found.</p>");
            } else {
                out.println("<table>");
                out.println("<tr><th>ID</th><th>Brand</th><th>Model</th><th>Year</th></tr>");
                for (Vehicle v : vehicles) {
                    out.println("<tr>");
                    out.println("<td>" + v.getId() + "</td>");
                    out.println("<td>" + v.getBrand() + "</td>");
                    out.println("<td>" + v.getModel() + "</td>");
                    out.println("<td>" + v.getYear() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            out.println("<a href='search.html'>Search Again</a>");
            out.println("<a href='register.html'>Register Another Vehicle</a>");

            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
