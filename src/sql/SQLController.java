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
    }

    public static void Conn() {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");
            System.out.println("База Подключена!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void CreateDB() {
        try {
            statement = connection.createStatement();
            statement.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
