import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class CookieDashboard extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Cookie[] cookies = request.getCookies();
        String name = null;

        if(cookies != null){
            for(Cookie ck : cookies){
                if(ck.getName().equals("username")){
                    name = ck.getValue();
                }
            }
        }

        if(name == null){
            out.println("<h3>No cookie found. Login again.</h3>");
        } else {
            out.println("<h2>Cookie Dashboard</h2>");
            out.println("<h3>Hello "+name+"</h3>");
        }
    }
}
