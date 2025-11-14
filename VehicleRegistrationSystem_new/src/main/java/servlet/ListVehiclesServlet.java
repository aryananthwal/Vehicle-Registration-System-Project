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

/**
 * Shows all vehicles in the database with Edit/Delete options.
 */
@WebServlet("/ListVehiclesServlet")
public class ListVehiclesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // messages from redirect (Delete or Update)
        String message = request.getParameter("message"); // green
        String error   = request.getParameter("error");   // red

        try {
            VehicleDAO vehicleDAO = new VehicleDAO();
            List<Vehicle> vehicles = vehicleDAO.getAllVehicles();

            // HTML start
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'><title>Vehicle List</title>");
            out.println("<style>");
            out.println("body{font-family:Arial,sans-serif;background:#f8f9fa;padding:20px;text-align:center;}");
            out.println("table{width:80%;margin:auto;border-collapse:collapse;background:#fff;}");
            out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
            out.println("th{background:#007BFF;color:#fff;}");
            out.println("a{text-decoration:none;color:#007BFF;font-weight:bold;}a:hover{text-decoration:underline;}");
            out.println(".msg{color:green;font-weight:bold;margin:10px;}");
            out.println(".error{color:red;font-weight:bold;margin:10px;}");
            out.println("</style></head><body>");

            out.println("<h2>Registered Vehicles</h2>");

            // show messages
            if (message != null && !message.isEmpty()) {
                out.println("<div class='msg'>" + message + "</div>");
            }
            if (error != null && !error.isEmpty()) {
                out.println("<div class='error'>" + error + "</div>");
            }

            if (vehicles.isEmpty()) {
                out.println("<p>No vehicles found.</p>");
            } else {
                out.println("<table>");
                out.println("<tr><th>Vehicle ID</th><th>Customer ID</th><th>Brand</th><th>Model</th><th>Year</th><th>Actions</th></tr>");
                for (Vehicle v : vehicles) {

                    // We'll pass only ID to Edit servlet; it will load DB record
                    String editUrl = "EditVehicleServlet?id=" + v.getId();
                    String deleteUrl = "DeleteVehicleServlet?id=" + v.getId();

                    out.println("<tr>");
                    out.println("<td>" + v.getId() + "</td>");
                    out.println("<td>" + v.getCustomerId() + "</td>");
                    out.println("<td>" + safe(v.getBrand()) + "</td>");
                    out.println("<td>" + safe(v.getModel()) + "</td>");
                    out.println("<td>" + v.getYear() + "</td>");
                    out.println("<td>"
                            + "<a href='" + editUrl + "'>Edit</a> | "
                            + "<a href='" + deleteUrl + "' onclick=\"return confirm('Delete vehicle "
                            + v.getId() + "?');\">Delete</a>"
                            + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            out.println("<p style='margin-top:20px;'>");
            out.println("<a href='register.html'>Register Another Vehicle</a> | ");
            out.println("<a href='search.html'>Search by Customer ID</a> | ");
            out.println("<a href='index.html'>Home</a>");
            out.println("</p>");

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace(); // server console
            out.println("<h3 class='error'>Error: " + e.getMessage() + "</h3>");
        }
    }

    // helper to avoid printing null
    private String safe(String s) {
        return (s == null) ? "" : s;
    }
}
