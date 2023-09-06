package by.javaguru.git.mergeexperience;

import java.io.*;

import by.javaguru.git.mergeexperience.topics.Module1Topics;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/module1")
public class Module1Servlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Модуль 1";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");

        out.println("<table>");
        for (Module1Topics value : Module1Topics.values())
            if (value.getOrder() == 1) {
                out.println("<tr><td>"
                            + value.getOrder() + "</td><td>"
                            + value.getTopic() + "</td><td>"
                            + value.getDesc()
                            + "</td><td><a href='javaTechnologies.jsp'>Подробнее</a>"
                            + "</td></tr>");
            } else {
                out.println("<tr><td>"
                            + value.getOrder() + "</td><td>"
                            + value.getTopic() + "</td><td>"
                            + value.getDesc()
                            + "</td><td><a href='notReady.jsp'>Подробнее</a>"
                            + "</td></tr>");
            }

        out.println("</table>");

        out.println("<a href='module2'>");
        out.println("<button>");
        out.println("<img src='right.png' />");
        out.println("</button>");
        out.println("</a>");


        out.println("</body></html>");
    }

    public void destroy() {
    }
}