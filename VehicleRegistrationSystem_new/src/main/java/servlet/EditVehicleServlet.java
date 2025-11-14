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

@WebServlet("/EditVehicleServlet")
public class EditVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Step 1: Handle GET request - Show Edit Form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            VehicleDAO vehicleDAO = new VehicleDAO();
            Vehicle vehicle = vehicleDAO.getVehicleById(id);

            if (vehicle == null) {
                out.println("<h3 style='color:red;'>Vehicle not found.</h3>");
                return;
            }

            // Edit Form
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Edit Vehicle</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f8f9fa; text-align: center; padding: 20px; }");
            out.println("form { display: inline-block; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
            out.println("input[type=text], input[type=number] { padding: 8px; margin: 5px; width: 200px; }");
            out.println("input[type=submit] { padding: 8px 15px; background: #007BFF; color: #fff; border: none; border-radius: 4px; cursor: pointer; }");
            out.println("input[type=submit]:hover { background: #0056b3; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<h2>Edit Vehicle</h2>");
            out.println("<form method='post' action='EditVehicleServlet'>");
            out.println("<input type='hidden' name='id' value='" + vehicle.getId() + "'/>");
            out.println("Brand: <input type='text' name='brand' value='" + vehicle.getBrand() + "' required/><br>");
            out.println("Model: <input type='text' name='model' value='" + vehicle.getModel() + "' required/><br>");
            out.println("Year: <input type='number' name='year' value='" + vehicle.getYear() + "' required/><br>");
            out.println("<input type='submit' value='Update Vehicle'/>");
            out.println("</form>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }

    // Step 2: Handle POST request - Update Vehicle
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            int year = Integer.parseInt(request.getParameter("year"));

            Vehicle vehicle = new Vehicle();
            vehicle.setId(id);
            vehicle.setBrand(brand);
            vehicle.setModel(model);
            vehicle.setYear(year);

            VehicleDAO vehicleDAO = new VehicleDAO();
            boolean updated = vehicleDAO.updateVehicle(vehicle);

            if (updated) {
                response.sendRedirect("ListVehiclesServlet?message=Vehicle+Updated+Successfully!");
            } else {
                out.println("<h3 style='color:red;'>Error: Unable to update vehicle.</h3>");
            }

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
