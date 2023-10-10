package by.javaguru.git.mergeexperience;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

import by.javaguru.git.mergeexperience.topics.Module1Topics;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/module1")
public class Module1Servlet extends HttpServlet {

    public static final String PATH_IMAGES;

    public static final String LEFT_ARROW_IMAGE = "icon-left-arrow.png";
    public static final String RIGHT_ARROW_IMAGE = "icon-right-arrow.png";

    static {
        try {
            PATH_IMAGES = Path.of(
                    Objects.requireNonNull(
                            Module1Servlet.class.getClassLoader().getResource("images")
                    ).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

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
            out.println("<tr><td>"
                    + value.getOrder() + "</td><td>"
                    + value.getTopic() + "</td><td>"
                    + value.getDesc()
                    + "</td><td><a href=''>Подробнее</a>"
                    + "</td></tr>");

        out.println("</table>");

        out.println("<div id=\"buttons\" style=\"float:left; height:100%; width:100%\">");
        out.println("<div id=\"previous\" style=\"float:left; width:25%; \">");
        out.println("<button type=\"button\">");
        out.println("<img src=\"" + PATH_IMAGES + "/" + LEFT_ARROW_IMAGE + "\" alt=\"<- Предыдущий модуль\">");
        out.println("</button>");
        out.println("</div>");

        out.println("<div id=\"next\" style=\"float:left; width:25%; \">");
        out.println("<button type=\"button\">");
        out.println("<img src=\"" + PATH_IMAGES  + "/" +  RIGHT_ARROW_IMAGE + "\" alt=\"Следующий модуль ->\">");
        out.println("</button>");
        out.println("</div>");
        out.println("</div>");

        out.println("<h1>" + PATH_IMAGES + "</h1>");

        out.println("</body></html>");
    }

    public void destroy() {
    }
}