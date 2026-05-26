import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class CookieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("uname");

        Cookie ck = new Cookie("username", name);
        ck.setMaxAge(300);
        response.addCookie(ck);

        out.println("<h2>Welcome " + name + "</h2>");
        out.println("<a href='CookieDashboard'>Go to Cookie Dashboard</a>");
    }
}
