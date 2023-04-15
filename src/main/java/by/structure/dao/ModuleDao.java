package by.structure.dao;

import by.structure.dao.exception.DaoException;
import by.structure.entity.Module;
import by.structure.utils.DbManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModuleDao implements Dao<Long, Module> {

    private static final ModuleDao INSTANCE = new ModuleDao();

    private ModuleDao() {
    }

    public static ModuleDao getInstance() {
        return INSTANCE;
    }

    private static final String FIND_ALL = """
            SELECT id, name
            FROM course_structure.public.modules
            """;

    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    private static final String DELETE_SQL = """
            DELETE
            FROM course_structure.public.modules
            WHERE id = ?;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO course_structure.public.modules(name)
            VALUES (?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE course_structure.public.modules
            SET name = ?
            WHERE id = ?;
            """;

    @Override
    public Optional<Module> findById(Long id) {
        try (var connection = DbManager.get()) {
            return findById(id, connection);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public Optional<Module> findById(Long id, Connection connection) {
        try (var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            Module module = null;
            prepareStatement.setLong(1, id);
            var result = prepareStatement.executeQuery();
            if (result.next()) {
                module = buildModule(result);
            }
            return module != null
                    ? Optional.of(module)
                    : Optional.empty();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Module> findAll() {
        List<Module> moduleList = new ArrayList<>();
        try (var connection = DbManager.get();
             var statements = connection.createStatement()) {
            var result = statements.executeQuery(FIND_ALL);
            while (result.next()) {
                moduleList.add(buildModule(result));
            }
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return moduleList;
    }

    @Override
    public Optional<Module> save(Module entity) {
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, entity.getName());
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
    public boolean update(Module entity) {
        try (var connection = DbManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, entity.getName());
            prepareStatement.setLong(2,entity.getId());
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

    //-----------------------------------------------------------------------------------------

    private Module buildModule(ResultSet result) throws SQLException {
        Module module = new Module();
        module.setId(result.getLong("id"));
        module.setName(result.getString("name"));
        return module;
    }
}
