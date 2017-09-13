package sql;

import java.sql.*;

public class SQLController {
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    public SQLController(String projectId) {

        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\" + projectId + ".db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        conn(projectId);
        createTables();
    }

    public void conn(String projectId) {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + "\\" + projectId + ".db");
            System.out.println("База Подключена!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void createTables() {
        try {
            statement = connection.createStatement();
            statement.execute("CREATE TABLE if not exists 'step_complete' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'step_id' INT, 'percent_complete' DOUBLE, 'date' DATE, 'user' text);");

            statement.execute("CREATE TABLE if not exists 'config' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'group_type' INT, 'value' INT);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPercentComplete(Integer step_id, String value) {
        try {
            statement = connection.createStatement();
            statement.execute("INSERT INTO step_complete ('step_id', 'percent_complete', 'date', 'user')" +
                    "VALUES (" + step_id + ", " + value + ", CURRENT_TIMESTAMP , + '" + System.getProperty("user.name") + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
