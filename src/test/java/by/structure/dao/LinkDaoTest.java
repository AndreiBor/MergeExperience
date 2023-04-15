package by.structure.dao;

import by.structure.entity.Link;
import by.structure.entity.Theme;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LinkDaoTest {

    private final LinkDao linkDao = LinkDao.getInstance();
    private Link link;

    @Before
    public void setUp() throws Exception {
        link = new Link();
        link.setLink("http://example.link");
        Theme theme = new Theme();
        theme.setId(1L);
        link.setTheme(theme);
    }

    @Test
    public void findAllTest() {
        try {
            List<Link> linkList = linkDao.findAll();
            saveModel();
            assertNotEquals(linkList, linkDao.findAll());
            if (link.getId() != null) {
                linkDao.delete(link.getId());
                assertEquals(linkList, linkDao.findAll());
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
            assertEquals(link.getLink(), linkDao.findById(link.getId()).get().getLink());
            assertEquals(link.getTheme().getId(), linkDao.findById(link.getId()).get().getTheme().getId());
        } finally {
            deleteModel();
        }
    }

    @Test
    public void updateTest() {
        try {
            saveModel();
            link.setLink("https://github.com/YlianPaulouskiy?tab=repositories");
            Theme theme = new Theme();
            theme.setId(2L);
            link.setTheme(theme);
            linkDao.update(link);
            var optionalTicket = linkDao.findById(link.getId());
            assertEquals(link.getLink(), optionalTicket.get().getLink());
            assertEquals(link.getTheme().getId(), optionalTicket.get().getTheme().getId());
        } finally {
            deleteModel();
        }
    }

    @Test
    public void deleteTest() {
        try {
            List<Link> linksBeforeAdd = linkDao.findAll();
            saveModel();
            List<Link> linksAfterAdd = linkDao.findAll();
            linkDao.delete(link.getId());
            assertNotEquals(linksAfterAdd, linkDao.findAll());
            assertEquals(linksBeforeAdd, linkDao.findAll());
        } finally {
            deleteModel();
        }
    }


    private void saveModel() {
        Optional<Link> optionalTheme = linkDao.save(link);
        optionalTheme.ifPresent(value -> link = value);
    }

    private void deleteModel() {
        if (link.getId() != null) {
            linkDao.delete(link.getId());
        }
    }

}
