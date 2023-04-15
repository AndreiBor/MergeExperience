package by.structure.dao;

import by.structure.dao.exception.DaoException;
import by.structure.dto.LinkFilter;
import by.structure.entity.Link;
import by.structure.utils.DbManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LinkDao implements Dao<Long, Link> {

    private static final LinkDao INSTANCE = new LinkDao();

    private LinkDao() {
    }

    public static LinkDao getInstance() {
        return INSTANCE;
    }

    private static final String FIND_ALL = """
            SELECT id, link, theme_id
            FROM course_structure.public.links
            """;

    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    private static final String DELETE_SQL = """
            DELETE
            FROM course_structure.public.links
            WHERE id = ?;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO course_structure.public.links(link, theme_id)
            VALUES (?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE course_structure.public.links
            SET link = ?, theme_id = ?
            WHERE id = ?;
            """;

    @Override
    public Optional<Link> findById(Long id) {
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            Link link = null;
            prepareStatement.setLong(1, id);
            var result = prepareStatement.executeQuery();
            if (result.next()) {
                link = buildLink(result);
            }
            return link != null
                    ? Optional.of(link)
                    : Optional.empty();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Link> findAll() {
        List<Link> linksList = new ArrayList<>();
        try (var connection = DbManager.get();
             var statements = connection.createStatement()) {
            var result = statements.executeQuery(FIND_ALL);
            while (result.next()) {
                linksList.add(buildLink(result));
            }
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return linksList;
    }

    public List<Link> findAll(LinkFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.link() != null) {
            whereSql.add(" link LIKE '%' || lower(?) || '%' ");
            parameters.add(filter.link());
        }
        if (filter.themeId() != null) {
            whereSql.add("theme_id = ?");
            parameters.add(filter.themeId());
        }
        String where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 0 ? " WHERE " : " ",
                ";"
        ));
        String sql = FIND_ALL.concat(where);

        List<Link> linksList = new ArrayList<>();
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                prepareStatement.setObject(i + 1, parameters.get(i));
            }
            var result = prepareStatement.executeQuery();
            while (result.next()) {
                linksList.add(buildLink(result));
            }
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return linksList;
    }

    @Override
    public Optional<Link> save(Link entity) {
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, entity.getLink());
            prepareStatement.setLong(2, entity.getTheme().getId());

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
    public boolean update(Link entity) {
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, entity.getLink());
            prepareStatement.setLong(2, entity.getTheme().getId());
            prepareStatement.setLong(3, entity.getId());
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

    private Link buildLink(ResultSet result) throws SQLException {
        Link link = new Link();
        link.setId(result.getLong("id"));
        link.setLink(result.getString("link"));
        link.setTheme(ThemeDao.getInstance()
                .findById(result.getLong("theme_id"),
                        result.getStatement().getConnection()).orElse(null));
        return link;
    }
}
