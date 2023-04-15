package by.structure.dao;

import by.structure.dao.exception.DaoException;
import by.structure.dto.ThemeFilter;
import by.structure.entity.Theme;
import by.structure.utils.DbManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ThemeDao implements Dao<Long, Theme> {

    private static final ThemeDao INSTANCE = new ThemeDao();

    private static final String FIND_ALL = """
            SELECT id, name, description, duration, module_id
            FROM course_structure.public.themes
            """;

    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    private static final String DELETE_SQL = """
            DELETE
            FROM course_structure.public.themes
            WHERE id = ?;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO course_structure.public.themes(name, description, duration, module_id)
            VALUES (?, ?,  ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE course_structure.public.themes
            SET name = ?, description = ?, duration = ?, module_id = ?
            WHERE id = ?;
            """;

    private ThemeDao() {
    }

    public static ThemeDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Theme> findById(Long id) {
        try (var connection = DbManager.get()) {
            return findById(id, connection);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public Optional<Theme> findById(Long id, Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            Theme theme = null;
            prepareStatement.setLong(1, id);
            var result = prepareStatement.executeQuery();
            if (result.next()) {
                theme = buildTheme(result);
            }
            return theme != null
                    ? Optional.of(theme)
                    : Optional.empty();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Theme> findAll() {
        List<Theme> themeList = new ArrayList<>();
        try (var connection = DbManager.get();
             var statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(FIND_ALL);
            while (result.next()) {
                themeList.add(buildTheme(result));
            }
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return themeList;
    }

    public List<Theme> findAll(ThemeFilter filter) {
        List<String> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();

        if (filter.name() != null) {
            whereSql.add("name = ?");
            parameters.add(filter.name());
        }
        if (filter.description() != null) {
            whereSql.add("description = ?");
            parameters.add(filter.description());
        }
        if (filter.duration() != null) {
            whereSql.add("duration = ?");
            parameters.add(filter.duration().toString());
        }
        if (filter.moduleId() != null) {
            whereSql.add("module_id = ?");
            parameters.add(filter.moduleId().toString());
        }

        var where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 0 ? " WHERE " : " ",
                ";"
        ));
        String sql = FIND_ALL + where;

        List<Theme> themeList = new ArrayList<>();
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                if (whereSql.get(i).contains("module_id")) {
                    prepareStatement.setObject(i + 1, Integer.parseInt(parameters.get(i)));
                } else {
                    prepareStatement.setObject(i + 1, parameters.get(i));
                }
            }
            var result = prepareStatement.executeQuery();
            while (result.next()) {
                themeList.add(buildTheme(result));
            }
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return themeList;
    }

    @Override
    public Optional<Theme> save(Theme entity) {
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, entity.getName());
            prepareStatement.setString(2, entity.getDescription());
            prepareStatement.setLong(3, entity.getDuration().toMillis());
            prepareStatement.setInt(4, entity.getModule().getId().intValue());

            prepareStatement.executeUpdate();

            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong("id"));
            }

            return entity.getId() != null
                    ? Optional.of(entity)
                    : Optional.empty();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public boolean update(Theme entity) {
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, entity.getName());
            prepareStatement.setString(2, entity.getDescription());
            prepareStatement.setLong(3, entity.getDuration().toMillis());
            prepareStatement.setLong(4, entity.getModule().getId());
            prepareStatement.setLong(5, entity.getId());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    //---------------------------------------------------------------------------------

    private Theme buildTheme(ResultSet result) throws SQLException {
        Theme theme = new Theme();
        theme.setId(result.getLong("id"));
        theme.setName(result.getString("name"));
        theme.setDescription(result.getString("description"));
        theme.setDuration(Duration.ofMillis(result.getLong("duration")));
        theme.setModule(ModuleDao.getInstance()
                .findById(result.getLong("module_id"), result.getStatement().getConnection())
                .orElse(null));
        return theme;
    }
}
