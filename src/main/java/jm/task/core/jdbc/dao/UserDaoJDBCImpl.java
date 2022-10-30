package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao, AutoCloseable {

    private static String CREATE_TABLE = "CREATE TABLE `testbase`.`users` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NULL,\n" +
            "  `lastname` VARCHAR(45) NULL,\n" +
            "  `age` TINYINT(1) UNSIGNED NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
    private static String DROP_TABLE = "DROP TABLE users;";
    private static String ADD_USER = "insert into users (name, lastname, age) values(?, ?, ?);";
    private static String REMOVE_USER = "delete from users where id = ?;";
    private Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(DROP_TABLE);

        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = util.getConnection().prepareStatement(ADD_USER)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Ошибка добавления пользователя");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = util.getConnection().prepareStatement(REMOVE_USER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя");
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from users;");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                result.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить всех пользователей (Таблицы нет)");
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("delete from users;");
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы");
        }
    }

    @Override
    public void close() throws Exception {
        util.close();
    }
}
