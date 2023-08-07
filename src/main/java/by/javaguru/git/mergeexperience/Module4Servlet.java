package by.javaguru.git.mergeexperience;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/module4")
public class Module4Servlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Модуль 4";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");

        out.println("<div style='text-align: center;'>");
        out.println("<button onclick=\"location.href='/module3'\"><- Предыдущий модуль</button>");
        out.println("<button onclick=\"location.href='/module1'\">Следующий модуль -></button>");
        out.println("</div>");

        out.println("</body></html>");
    }

    public void destroy() {
    }
}