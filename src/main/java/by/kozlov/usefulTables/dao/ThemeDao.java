package by.kozlov.usefulTables.dao;

import by.kozlov.usefulTables.entity.LinkDB;
import by.kozlov.usefulTables.entity.ModuleDB;
import by.kozlov.usefulTables.entity.ThemeDB;
import by.kozlov.usefulTables.exception.DaoException;
import by.kozlov.usefulTables.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThemeDao {

    private static final ThemeDao INSTANCE = new ThemeDao();

    private static String FIND_ALL = """
            SELECT th.id, 
            th.name_of_theme,
            th.id_module, 
            m.name_of_module       
            FROM themes as th
            LEFT JOIN modules as m on m.id = th.id_module
            """;

    private static String FIND_BY_ID = FIND_ALL + """
            WHERE th.id = ?
            """;

    private static String FIND_BY_MODULE = FIND_ALL + """
            WHERE th.id_module = ?
            """;

    public List<ThemeDB> findAllByModule(ModuleDB module) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_MODULE)) {
            List<ThemeDB> themes = new ArrayList<>();
            statement.setInt(1, module.getId());
            var result = statement.executeQuery();
            while (result.next())
                themes.add(buildThemeDB(result));
            return themes;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<ThemeDB> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            ThemeDB themeDB = null;
            statement.setInt(1, id);
            var result = statement.executeQuery();
            if (result.next())
                themeDB = buildThemeDB(result);
            return Optional.ofNullable(themeDB);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<ThemeDB> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<ThemeDB> themes = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                themes.add(buildThemeDB(result));

            return themes;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ThemeDB buildThemeDB(ResultSet result) throws SQLException {
        var module = new ModuleDB(
                result.getInt("id_module"),
                result.getString("name_of_module")
        );
        return new ThemeDB(
                result.getInt("id"),
                result.getString("name_of_theme"),
                module
        );
    }

    private ThemeDao() {}

    public static ThemeDao getInstance() {
        return INSTANCE;
    }
}
