package by.itacademy.git.mergeexperience;

import by.itacademy.git.mergeexperience.topics.Module2Topics;
import by.kozlov.usefulTables.entity.LinkDB;
import by.kozlov.usefulTables.entity.Modules;
import by.kozlov.usefulTables.entity.ThemeDB;
import by.kozlov.usefulTables.service.ModuleService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/module2")
public class Module2Servlet extends HttpServlet {

    private static final ModuleService moduleService = ModuleService.getInstance();//by kozlov
    private String message;
    private String message1;
    private String message2;
    public void init() {
        message = "Модуль 2";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();

        //by Kozlov
        String tableStyle = "table.myTable,table.myTable th,table.myTable tr,table.myTable td{ width: 450px;" +
                "height:30px;" + "border: solid 1px silver;" +
                "text-align:center;" + "border-collapse: collapse;}";
        List<ThemeDB> listModuleTwo = moduleService.findThemesByModule(Modules.MODULE2.getModuleDB()); //by Kozlov

        out.println("<html>");

        //by Kozlov
        out.println("<head><style>");
        out.println(tableStyle);
        out.println("</style></head>");//by Kozlov

        out.println("<body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<caption>Модуль 2</caption>");
        for (Module2Topics value : Module2Topics.values())
            out.println("<tr><td>"
                    + value.getOrder() + "</td><td>"
                    + value.getTopic() + "</td><td>"
                    + value.getDesc()   + "</td><td>"
                    + value.getHours()
                    + "</td><td><a href='not_implemented_yet.html'>Подробнее</a>"
                    + "</td></tr>");

        //by Kozlov
        out.println("<br><br><table class = \"myTable\"><caption><b>Useful links on topics</b></caption>");
        out.println("<thead><tr><th>Theme</th><th>Links</th></tr></thead><tbody>");
        for (ThemeDB value : listModuleTwo) {
            out.println("<tr><td>" + value.getName_of_theme() + "</td><td>");
            for (LinkDB link : value.getLinks()) {
                out.println("<a href=\"" + link.getLink() + "\" target=\"_blank\">" + link.getDescription() + "</a><br>");
            }
            out.println("</td></tr>");
        }
        out.println("</tbody></table>"); //by Kozlov


        out.println("</br><a href=\"/module1\">&#8592Предыдущий модуль</a>");
        out.println("</br><a href=\"/module3\">Следующий модуль&#8594</a>");
        out.println("</br><a href='/'>Назад</a>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}