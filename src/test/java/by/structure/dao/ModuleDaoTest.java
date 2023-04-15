package by.structure.dao;

import by.structure.entity.Module;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ModuleDaoTest {

    private final ModuleDao moduleDao = ModuleDao.getInstance();
    private Module module;

    @Before
    public void setUp() throws Exception {
        module = new Module();
        module.setName("Тест модуль");
    }

    @Test
    public void findAllTest() {
        try {
            List<Module> moduleList = moduleDao.findAll();
            saveModel();
            assertNotEquals(moduleList, moduleDao.findAll());
            if (module.getId() != null) {
                moduleDao.delete(module.getId());
                assertEquals(moduleList, moduleDao.findAll());
            }
        } finally {
            deleteModel();
        }
    }


    @Test
    public void saveTest() {
        findAllTest();
        findByIdTest();
        deleteTest();
    }

    @Test
    public void findByIdTest() {
        try {
            saveModel();
            assertEquals(module.getName(), moduleDao.findById(module.getId()).get().getName());
        } finally {
            deleteModel();
        }
    }

    @Test
    public void updateTest() {
        try {
            saveModel();
            module.setName("New module name");
            moduleDao.update(module);
            var optionalTicket = moduleDao.findById(module.getId());
            assertEquals(module.getName(), optionalTicket.get().getName());
        } finally {
            deleteModel();
        }
    }

    @Test
    public void deleteTest() {
        try {
            List<Module> modulesBeforeAdd = moduleDao.findAll();
            saveModel();
            List<Module> modulesAfterAdd = moduleDao.findAll();
            moduleDao.delete(module.getId());
            assertNotEquals(modulesAfterAdd, moduleDao.findAll());
            assertEquals(modulesBeforeAdd, moduleDao.findAll());
        } finally {
            deleteModel();
        }
    }


    private void saveModel() {
        Optional<Module> optionalModule = moduleDao.save(module);
        optionalModule.ifPresent(value -> module = value);
    }

    private void deleteModel() {
        if (module.getId() != null) {
            moduleDao.delete(module.getId());
        }
    }
}
