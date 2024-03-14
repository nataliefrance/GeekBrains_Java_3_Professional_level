package lesson_2;

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement statement;


    public static void main(String[] args) {
        try {
            connect("usersDB");
            createTableEmployee();
            printTableMetaData("employees");
            addEmployee("Malvina", 100000);
            printTwoColumnTable("employees", "name", "salary");
            deleteByID("employees", 3);
            printTwoColumnTable("employees", "name", "salary");
            getByID("employees", 2);
            disconnect();

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
        statement = connection.createStatement();
        System.out.println("Connection to DB successfully");
    }

    public static void disconnect() throws SQLException {
        connection.close();
        System.out.println("Disconnection to DB successfully");
    }

    public static void createTableEmployee() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS employees " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "salary INTEGER)");
    }

    public static void printTwoColumnTable(String tableName, String firstColumnLabel, String secondColumnLabel) throws SQLException {
        //Экземпляры этого типа содержат данные, которые были получены в результате выполнения SELECT запроса
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        System.out.printf("TABLE %s:\n", tableName);
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(firstColumnLabel) + " " + rs.getString(secondColumnLabel));
        }
        System.out.println();
    }

    public static void printTableMetaData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        //из ResultSet достаём метаданные о состоянии таблицы.
        ResultSetMetaData rsmd = rs.getMetaData();
        System.out.printf("TABLE METADATA %s: \n", tableName);
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            System.out.println("name: " + rsmd.getColumnName(i) + ", type: " + rsmd.getColumnType(i));
        }
        System.out.println();
    }

    public static void addEmployee(String name, int salary) throws SQLException {
        System.out.printf("Adding new employee %s...\n", name);
        statement.executeUpdate("INSERT INTO employees(name, salary) VALUES ('" + name + "', " + salary + ")");
        System.out.println("Adding successfully");
        System.out.println();
    }

    public static void deleteByID(String tableName, int id) throws SQLException {
        System.out.printf("Start deleting from %s with id %d...\n", tableName, id);
        if (getByID(tableName, id)) {
            statement.executeUpdate(String.format("DELETE FROM %s WHERE id = %d", tableName, id));
            System.out.printf("Delete from %s with id %d was successfully", tableName, id);
        } else {
            System.out.println("Deletion failed");
        }
        System.out.println();
    }

    public static boolean getByID(String tableName, int id) throws SQLException {
        System.out.printf("Getting from %s by id %d...\n", tableName, id);
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE id = %d", tableName, id));
        if (!resultSet.next()) {
            System.out.println("String not found");
            return false;
        } else {
            System.out.println(resultSet.getInt("id") + " " + resultSet.getString(2) + " " + resultSet.getString(3));
            System.out.println();
            return true;
        }
    }
}
