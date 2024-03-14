package lesson_2;

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement stmt;


    public static void main(String[] args) {
        try {
            connect("usersDB");
            createTable();
            printTableMetaData("employees");
            addEmployee("Lidia", 50000);
            printTwoColumnTable("employees", "name", "salary");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connect(String dataBaseName) throws ClassNotFoundException, SQLException {
        //Class.forName("org.sqlite.JDBC");
        //DriverManager – Менеджер драйверов, управляющий списком драйверов БД и позволяющий открыть соединение с базой данных
        //Метод getConnection на основании параметра URL находит java.sql.Driver соответствующей базы данных и вызывает у него метод connect.
        connection = DriverManager.getConnection("jdbc:sqlite:" + dataBaseName);
        //stmt всегда возвращает таблицу
        stmt = connection.createStatement();
    }
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS employees " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "salary INTEGER)");
    }

    public static void printTwoColumnTable(String tableName, String firstColumnLabel, String secondColumnLabel) throws SQLException {
        //Экземпляры этого типа содержат данные, которые были получены в результате выполнения SELECT запроса
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

        while(rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(firstColumnLabel) + " " + rs.getString(secondColumnLabel));
        }
    }

    public static void printTableMetaData(String tableName) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
        //из ResultSet достаём метаданные о состоянии таблицы.
        ResultSetMetaData rsmd = rs.getMetaData();

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            System.out.println("name: " + rsmd.getColumnName(i) + ", type: " + rsmd.getColumnType(i));
        }
    }

    public static void addEmployee(String name, int salary) throws SQLException {
        stmt.executeUpdate("INSERT INTO employees(name, salary) VALUES ('" + name + "', " + salary + ")");
    }
}
