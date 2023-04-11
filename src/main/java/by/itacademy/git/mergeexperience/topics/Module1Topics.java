package by.itacademy.git.mergeexperience.topics;

public enum Module1Topics {
    JAKARTA(1, "Jakarta", "Обзор java enterprise технологий"),
    MAVEN(2, "Apache Maven", "Изучение сборщика проектов Maven"),
    TOMCAT(3, "Apache Tomcat", "Изучение основ HTTP, TCP/IP и web сервера Tomcat"),
    SERVLET(4, "HttpServlet", "Освоение технологии java сервлетов"),
    GIT(5, "GIT ", "Работа с системой контроля версий GIT");

    private int order;
    private String topic;
    private String desc;

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
}
