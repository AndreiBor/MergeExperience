package by.kozlov.usefulTables.entity;

import java.util.List;

public class ThemeDB {

    private Integer id;
    private String name_of_theme;
    private ModuleDB module;
    private List<LinkDB> links;

    public ThemeDB(Integer id, String name_of_theme, ModuleDB module) {
        this.id = id;
        this.name_of_theme = name_of_theme;
        this.module = module;
    }

    public ThemeDB(String name_of_theme, ModuleDB module) {
        this.name_of_theme = name_of_theme;
        this.module = module;
    }

    public ThemeDB() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName_of_theme() {
        return name_of_theme;
    }

    public void setName_of_theme(String name_of_theme) {
        this.name_of_theme = name_of_theme;
    }

    public ModuleDB getModule() {
        return module;
    }

    public void setModule(ModuleDB module) {
        this.module = module;
    }

    public List<LinkDB> getLinks() {
        return links;
    }

    public void setLinks(List<LinkDB> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "ThemeDB{" +
                "id=" + id +
                ", name_of_theme='" + name_of_theme + '\'' +
                ", module=" + module +
                '}';
    }
}
