package by.kozlov.usefulTables.dao;

import by.kozlov.usefulTables.entity.LinkDB;
import by.kozlov.usefulTables.entity.ModuleDB;
import by.kozlov.usefulTables.entity.ThemeDB;
import by.kozlov.usefulTables.exception.DaoException;
import by.kozlov.usefulTables.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinkDao implements Dao<Integer, LinkDB> {

    private static final LinkDao INSTANCE = new LinkDao();

    private static String SAVE_SQL = """
            INSERT INTO links (description,link,id_theme) 
            VALUES (?, ?, ?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM links
            WHERE id = ?
            """;

    private static String FIND_ALL = """
            SELECT l.id, 
            l.description, 
            l.link, 
            l.id_theme, 
            th.name_of_theme,
            th.id_module, 
            m.name_of_module       
            FROM links as l
            LEFT JOIN themes as th on th.id = l.id_theme
            LEFT JOIN modules as m on m.id = th.id_module
            """;

    private static String UPDATE_SQL = """
            UPDATE links SET
            description = ?,
            link = ?,
            id_theme = ?
            WHERE id = ?
            """;

    private static String FIND_BY_ID = FIND_ALL + """
            WHERE t.id = ?
            """;

    private static String FIND_BY_THEME = FIND_ALL + """
            WHERE l.id_theme = ?
            """;

    @Override
    public boolean update(LinkDB linkDB) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, linkDB.getDescription());
            statement.setString(2, linkDB.getLink());
            statement.setInt(3, linkDB.getTheme().getId());
            statement.setInt(4, linkDB.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<LinkDB> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            LinkDB linkDB = null;
            statement.setInt(1, id);
            var result = statement.executeQuery();
            if (result.next())
                linkDB = buildLinkDB(result);
            return Optional.ofNullable(linkDB);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<LinkDB> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<LinkDB> links = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                links.add(buildLinkDB(result));

            return links;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<LinkDB> findAllByTheme(ThemeDB theme) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_THEME)) {
            List<LinkDB> links = new ArrayList<>();
            statement.setInt(1, theme.getId());
            var result = statement.executeQuery();
            while (result.next())
                links.add(buildLinkDB(result));
            return links;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public LinkDB save(LinkDB linkDB) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, linkDB.getDescription());
            statement.setString(2, linkDB.getLink());
            statement.setInt(3, linkDB.getTheme().getId());

            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                linkDB.setId(generatedKeys.getInt("id"));
            return linkDB;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private LinkDB buildLinkDB(ResultSet result) throws SQLException {
        var module = new ModuleDB(
                result.getInt("id_module"),
                result.getString("name_of_module")
        );
        var theme = new ThemeDB(
                result.getInt("id_theme"),
                result.getString("name_of_theme"),
                module
        );
        return new LinkDB(
                result.getInt("id"),
                result.getString("description"),
                result.getString("link"),
                theme
        );
    }

    private LinkDao() {}

    public static LinkDao getInstance() {
        return INSTANCE;
    }

}
