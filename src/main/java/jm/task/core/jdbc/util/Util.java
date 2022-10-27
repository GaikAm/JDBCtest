package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private final String URL =
            "jdbc:mysql://localhost:3306/testbase";
    private final String USERNAME = "root";
    private final String PASSWORD = "Gaik-20011412";

    private Connection connection;

    private SessionFactory sessionFactory;


    public Util() {
        // === JDBC ===
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.out.println("Драйвер не зарегестрировался!");
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (connection.isClosed()) {
                System.out.println("Соединение не установлено!");
            }
        } catch (SQLException e) {
            System.out.println("Соединение не установлено!");
        }

        // === Hibernate ===
        /*try {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (HibernateException e) {
            System.out.println("Соединение не установлено!");
        }*/
    }

    public Connection getConnection() { return connection; }

    public SessionFactory getSessionFactory() { return sessionFactory; }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
        sessionFactory.close();
    }
}
