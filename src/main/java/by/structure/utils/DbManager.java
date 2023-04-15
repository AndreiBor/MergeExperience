package by.structure.utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DbManager {

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> connectionPool;
    private static List<Connection> connectionList;

    static {
        initConnectionPool();
        initConnectionList();
    }

    private static void initConnectionList() {
        connectionList = new ArrayList<>(connectionPool);
    }

    private static void initConnectionPool() {
        var poolSize = PropertyUtils.get(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.valueOf(poolSize);

        connectionPool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            var connection = open();
            var proxyConnection = (Connection) Proxy.newProxyInstance(DbManager.class.getClassLoader(), new Class[]{Connection.class},
                    ((proxy, method, args) ->
                            method.getName().equals("close")
                                    ? connectionPool.add((Connection) proxy)
                                    : method.invoke(connection, args)
                    ));
            connectionPool.add(proxyConnection);
        }
    }


    private static Connection open() {
        try {
            return DriverManager.getConnection(PropertyUtils.get(URL_KEY), PropertyUtils.get(USERNAME_KEY), PropertyUtils.get(PASSWORD_KEY));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Получаем соединение с базой из пула соединений
     *
     * @return соединение
     */
    public static Connection get() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Закрывает все соединения с базой данной
     */
    public static void shutDown() {
        for (Connection connection : connectionList) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
