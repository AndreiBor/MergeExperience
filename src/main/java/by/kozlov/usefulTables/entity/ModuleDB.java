package by.kozlov.usefulTables.entity;

import java.util.List;

public class ModuleDB {

    private Integer id;
    private String name_of_module;
    private List<ThemeDB> themes;

    public ModuleDB(Integer id, String name_of_module) {
        this.id = id;
        this.name_of_module = name_of_module;
    }

    public ModuleDB(String name_of_module) {
        this.name_of_module = name_of_module;
    }

    public ModuleDB() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName_of_module() {
        return name_of_module;
    }

    public void setName_of_module(String name_of_module) {
        this.name_of_module = name_of_module;
    }

    public List<ThemeDB> getThemes() {
        return themes;
    }

    public void setLinks(List<ThemeDB> themes) {
        this.themes = themes;
    }

    @Override
    public String toString() {
        return "ModuleDB{" +
                "id=" + id +
                ", name_of_module='" + name_of_module + '\'' +
                '}';
    }
}
