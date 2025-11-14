package servlet;

import dao.VehicleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteVehicleServlet")
public class DeleteVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String redirect;

        if (idStr == null || idStr.isEmpty()) {
            redirect = "ListVehiclesServlet?error=Missing+vehicle+ID";
        } else {
            try {
                int id = Integer.parseInt(idStr);
                VehicleDAO dao = new VehicleDAO();
                boolean deleted = dao.deleteVehicle(id);

                if (deleted) {
                    redirect = "ListVehiclesServlet?message=Vehicle+Deleted+Successfully!";
                } else {
                    redirect = "ListVehiclesServlet?error=Vehicle+not+found+or+delete+failed";
                }
            } catch (NumberFormatException nfe) {
                redirect = "ListVehiclesServlet?error=Invalid+vehicle+ID";
            }
        }

        response.sendRedirect(redirect);
    }
}
