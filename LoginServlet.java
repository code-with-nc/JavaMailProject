import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if("admin".equals(user) && "123".equals(pass)){
            out.println("<h2>Login Successful</h2>");
        } else {
            out.println("<h2>Invalid Login</h2>");
        }
    }
}
