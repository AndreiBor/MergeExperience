package by.structure.dao;

import by.structure.entity.Module;
import by.structure.entity.Theme;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ThemeDaoTest {

    private final ThemeDao themeDao = ThemeDao.getInstance();
    private Theme theme;

    @Before
    public void setUp() throws Exception {
        theme = new Theme();
        theme.setName("Test theme");
        theme.setDescription("TestDescription");
        theme.setDuration(Duration.parse("PT2H50M"));
        Module module = new Module();
        module.setId(1L);
        theme.setModule(module);
    }

    @Test
    public void findAllTest() {
        try {
            List<Theme> themeList = themeDao.findAll();
            saveModel();
            assertNotEquals(themeList, themeDao.findAll());
            if (theme.getId() != null) {
                themeDao.delete(theme.getId());
                assertEquals(themeList, themeDao.findAll());
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
            assertEquals(theme.getName(), themeDao.findById(theme.getId()).get().getName());
            assertEquals(theme.getDescription(), themeDao.findById(theme.getId()).get().getDescription());
            assertEquals(theme.getDuration(), themeDao.findById(theme.getId()).get().getDuration());
            assertEquals(theme.getModule().getId(), themeDao.findById(theme.getId()).get().getModule().getId());
        } finally {
            deleteModel();
        }
    }

    @Test
    public void updateTest() {
        try {
            saveModel();
            theme.setName("New Theme");
            theme.setDescription("DescriptionUpdate");
            themeDao.update(theme);
            var optionalTicket = themeDao.findById(theme.getId());
            assertEquals(theme.getName(), optionalTicket.get().getName());
            assertEquals(theme.getDescription(), optionalTicket.get().getDescription());
        } finally {
            deleteModel();
        }
    }

    @Test
    public void deleteTest() {
        try {
            List<Theme> themesBeforeAdd = themeDao.findAll();
            saveModel();
            List<Theme> themesAfterAdd = themeDao.findAll();
            themeDao.delete(theme.getId());
            assertNotEquals(themesAfterAdd, themeDao.findAll());
            assertEquals(themesBeforeAdd, themeDao.findAll());
        } finally {
            deleteModel();
        }
    }


    private void saveModel() {
        Optional<Theme> optionalTheme = themeDao.save(theme);
        optionalTheme.ifPresent(value -> theme = value);
    }

    private void deleteModel() {
        if (theme.getId() != null) {
            themeDao.delete(theme.getId());
        }
    }

}
