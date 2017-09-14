package sql;

import java.sql.*;
import java.util.HashMap;

public class SQLController {
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;
    private String dbPath;

    private HashMap<Integer, String> stepValue = new HashMap<>();

    public SQLController(String projectId) {

        dbPath = System.getProperty("user.dir") + "\\" + projectId + ".db";
        String url = "jdbc:sqlite:" + dbPath;

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
        fillStepMap();
    }

    public void conn(String projectId) {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
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

    private void fillStepMap() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(id) as id, step_id, percent_complete FROM step_complete");
            while (resultSet.next()) {
                stepValue.put(new Integer(resultSet.getInt("step_id")), resultSet.getString("percent_complete"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getStepValue(Integer stepId) {
        return stepValue.getOrDefault(stepId, null);
    }

    public String getDbPath() {
        return dbPath;
    }
}
