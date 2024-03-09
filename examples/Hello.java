import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import java.io.*;

public class Hello extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
  
        pw.println("<html><body>");
        pw.println("Welcome to servlet");
        pw.println("</body></html>");
        pw.close();
    }
}