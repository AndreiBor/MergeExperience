package by.kozlov.usefulTables.entity;

public class LinkDB {

    private Integer id;
    private String description;
    private String link;
    private ThemeDB theme;

    public LinkDB(Integer id, String description, String link, ThemeDB theme) {
        this.id = id;
        this.description = description;
        this.link = link;
        this.theme = theme;
    }

    public LinkDB(String description, String link, ThemeDB theme) {
        this.description = description;
        this.link = link;
        this.theme = theme;
    }

    public LinkDB() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ThemeDB getTheme() {
        return theme;
    }

    public void setTheme(ThemeDB theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "LinkDB{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", theme=" + theme +
                '}';
    }
}
