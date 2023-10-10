package by.javaguru.git.mergeexperience.topics;

public enum Module1Topics {
    JAKARTA(1,
            "Jakarta ",
            "Обзор java enterprise технологий",
            "jakarta_details.jsp"),
    MAVEN(2, "Apache Maven ", "Изучение сборщика проектов Maven"),
    TOMCAT(3, "Apache Tomcat ", "Изучение основ HTTP, TCP/IP и web сервера Tomcat"),
    SERVLET(4, "HttpServlet ", "Освоение технологии java сервлетов"),
    GIT(5, "GIT ", "Работа с системой контроля версий GIT");

    private int order;
    private String topic;
    private String desc;

    private String detailsFile = "no_details.jsp";

    Module1Topics(int order, String topic, String desc, String detailsFile) {
        this.order = order;
        this.topic = topic;
        this.desc = desc;
        this.detailsFile = detailsFile;
    }

    Module1Topics(int order, String topic, String desc) {
        this.order = order;
        this.topic = topic;
        this.desc = desc;
    }

    public int getOrder() {
        return order;
    }

    public String getTopic() {
        return topic;
    }

    public String getDesc() {
        return desc;
    }

    public String getDetailsFile() {
        return detailsFile;
    }
}
