package by.kozlov.usefulTables.exception;

import java.sql.SQLException;

public class DaoException extends RuntimeException {

    public DaoException(SQLException ex) {
        super(ex);
    }
}
