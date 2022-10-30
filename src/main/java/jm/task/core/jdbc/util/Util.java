package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util implements AutoCloseable {

    private final String URL =
            "jdbc:mysql://localhost:3306/testbase";
    private final String USERNAME = "root";
    private final String PASSWORD = "Gaik-20011412";

    private Connection connection;


    public Util() {
        // === JDBC ===
        /*try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.out.println("Драйвер не зарегестрировался!");
        }*/

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (connection.isClosed()) {
                System.out.println("Соединение не установлено!");
            }
        } catch (SQLException e) {
            System.out.println("Соединение не установлено!");
        }
    }

    public Connection getConnection() { return connection; }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
