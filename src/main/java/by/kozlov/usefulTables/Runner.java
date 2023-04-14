package by.kozlov.usefulTables;

import by.kozlov.usefulTables.dao.LinkDao;
import by.kozlov.usefulTables.dao.ThemeDao;
import by.kozlov.usefulTables.entity.ModuleDB;
import by.kozlov.usefulTables.entity.Modules;
import by.kozlov.usefulTables.service.ModuleService;


public class Runner {

    public static void main(String[] args) {

        var linkDao = LinkDao.getInstance();
        System.out.println(linkDao.findAll());
        var themeDao = ThemeDao.getInstance();
        var moduleService = ModuleService.getInstance();

        System.out.println(themeDao.findAllByModule(new ModuleDB(1,"MODULE 1")));

        System.out.println(Modules.MODULE1.getModuleDB().getId());
        System.out.println(moduleService.findThemesByModule(new ModuleDB(1,"MODULE 1")));


    }
}
