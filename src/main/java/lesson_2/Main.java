package lesson_2;

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement stmt;


    public static void main(String[] args) {
        try {
            connect();
            printTable();
            printMetaData();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        //Class.forName("org.sqlite.JDBC");
        //DriverManager – Менеджер драйверов, управляющий списком драйверов БД и позволяющий открыть соединение с базой данных
        //Метод getConnection на основании параметра URL находит java.sql.Driver соответствующей базы данных и вызывает у него метод connect.
        connection = DriverManager.getConnection("jdbc:sqlite:usersDB");
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

    public static void createDB() {

    }

    public static void printTable() throws SQLException {
        //Экземпляры этого типа содержат данные, которые были получены в результате выполнения SELECT запроса
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");

        while(rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString("name") + " " + rs.getString("score"));
        }
    }

    public static void printMetaData() throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");
        //из ResultSet достаём метаданные о состоянии таблицы.
        ResultSetMetaData rsmd = rs.getMetaData();

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            System.out.println("name: " + rsmd.getColumnName(i) + ", type: " + rsmd.getColumnType(i));
        }
    }
}
