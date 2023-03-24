package by.itacademy.git.mergeexperience;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/module3")
public class Module3Servlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Модуль 3";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</br><a href=\"/module2\">Предыдущий модуль</a>");
        out.println("</br><a href=\"/module4\">Следующий модуль</a>");
        out.println("</br><a href='/'>Назад</a>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}