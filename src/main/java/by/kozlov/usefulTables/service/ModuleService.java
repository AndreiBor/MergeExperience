package by.kozlov.usefulTables.service;

import by.kozlov.usefulTables.dao.LinkDao;
import by.kozlov.usefulTables.dao.ThemeDao;
import by.kozlov.usefulTables.entity.LinkDB;
import by.kozlov.usefulTables.entity.ModuleDB;
import by.kozlov.usefulTables.entity.ThemeDB;

import java.util.ArrayList;
import java.util.List;

public class ModuleService {

    private static final ModuleService INSTANCE = new ModuleService();
    private static final ThemeDao themeDao = ThemeDao.getInstance();
    private static final LinkDao linkDao = LinkDao.getInstance();



    public List<ThemeDB> findThemesByModule(ModuleDB module){

        List<ThemeDB> themes = themeDao.findAllByModule(module);
        for (ThemeDB theme: themes) {
            theme.setLinks(linkDao.findAllByTheme(theme));
        }
        return themes;
    }

    private ModuleService() {}

    public static ModuleService getInstance() {
        return INSTANCE;
    }


}
