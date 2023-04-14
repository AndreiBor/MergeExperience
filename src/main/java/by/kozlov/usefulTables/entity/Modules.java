package by.kozlov.usefulTables.entity;

public enum Modules {

    MODULE1(new ModuleDB(1,"MODULE 1")),
    MODULE2(new ModuleDB(2,"MODULE 2")),
    MODULE3(new ModuleDB(3,"MODULE 3")),
    MODULE4(new ModuleDB(4,"MODULE 4"));

    private ModuleDB moduleDB;

    Modules(ModuleDB module) {
        this.moduleDB = module;
    }

    public ModuleDB getModuleDB() {
        return moduleDB;
    }

    public void setModuleDB(ModuleDB moduleDB) {
        this.moduleDB = moduleDB;
    }
}
