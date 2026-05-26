import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("username") == null){
            out.println("<h3>No active session. Login again.</h3>");
        } else {
            String name = (String) session.getAttribute("username");
            out.println("<h2>Dashboard</h2>");
            out.println("<h3>Welcome "+name+"</h3>");
        }
    }
}
