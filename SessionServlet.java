import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class SessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("uname");

        HttpSession session = request.getSession();
        session.setAttribute("username", name);

        out.println("<h2>Welcome " + name + "</h2>");
        out.println("<a href='DashboardServlet'>Go to Dashboard</a>");
    }
}
