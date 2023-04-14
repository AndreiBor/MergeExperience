package by.itacademy.git.mergeexperience;

import by.itacademy.git.mergeexperience.topics.Module4Topics;
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

@WebServlet("/module4")
public class Module4Servlet extends HttpServlet {

    private static final ModuleService moduleService = ModuleService.getInstance();//by kozlov
    private String message;


    public void init() {
        message = "Модуль 4";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();

        //by Kozlov
        String tableStyle = "table.myTable,table.myTable th,table.myTable tr,table.myTable td{ width: 450px;" +
                "height:30px;" + "border: solid 1px silver;" +
                "text-align:center;" + "border-collapse: collapse;}";
        List<ThemeDB> listModuleFour = moduleService.findThemesByModule(Modules.MODULE4.getModuleDB()); //by Kozlov

        out.println("<html>");

        //by Kozlov
        out.println("<head><style>");
        out.println(tableStyle);
        out.println("</style></head>");//by Kozlov

        out.println("<body>");
        out.println("<h1>" + message + "</h1>");

        //by Nazar
        List<Module4Topics> topics = List.of(Module4Topics.values());

        out.println("<table border=\"1\">");
        out.println("<tr> <th>Номер темы</th> <th>Тема</th> <th>Описание</th> </tr>");
        for (Module4Topics topic : topics) {
            out.println("<tr>");
            out.println("<td>");
            out.println(topic.getOrder());
            out.println("</td>");
            out.println("<td>");
            out.println(topic.getTopic());
            out.println("</td>");
            out.println("<td>");
            out.println(topic.getDescription());
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");


        //by Kozlov
        out.println("<br><br><table class = \"myTable\"><caption><b>Useful links on topics</b></caption>");
        out.println("<thead><tr><th>Theme</th><th>Links</th></tr></thead><tbody>");
        for (ThemeDB value : listModuleFour) {
            out.println("<tr><td>" + value.getName_of_theme() + "</td><td>");
            for (LinkDB link : value.getLinks()) {
                out.println("<a href=\"" + link.getLink() + "\" target=\"_blank\">" + link.getDescription() + "</a><br>");
            }
            out.println("</td></tr>");
        }
        out.println("</tbody></table>"); //by Kozlov

        out.println("</br><a href=\"/module3\">&#8592Предыдущий модуль</a>");
        out.println("</br><a href='/'>Назад</a>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}