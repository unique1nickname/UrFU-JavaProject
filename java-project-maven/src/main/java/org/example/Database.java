package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Connection connection;

    private static Statement statement;

    public static void connectDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:java-project-maven/src/main/resources/database.db");
        statement = connection.createStatement();
    }

    public static void createTable() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS sportObjects (\n" +
                "id INTEGER NOT NULL,\n" +
                "name TEXT,\n" +
                "subject TEXT,\n" +
                "address TEXT,\n" +
                "date TEXT\n" +
                ");");
    }

    public static void inputData(ArrayList<SportObject> objects) throws SQLException {
        for (SportObject o : objects) {

            // На парах мы делали INSERT по другому, через format, но в интернете в основном пишут делать так.
            // Насколько я знаю, через format можно вставить свой SQL код в запрос, что не желательно.

            String insertSQL = "INSERT INTO sportObjects VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(insertSQL);
            ps.setInt(1, o.getId());
            ps.setString(2, o.getName());
            ps.setString(3, o.getSubjectRF());
            ps.setString(4, o.getFullAddress());
            ps.setString(5, o.getDateStr());
            ps.executeUpdate();
        }
    }

    public static ArrayList<SportObject> readData() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM sportObjects");
        ArrayList<SportObject> objects = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String subject = resultSet.getString("subject");
            String address = resultSet.getString("address");
            String date = resultSet.getString("date");
            objects.add(new SportObject(id, name, subject, address, date));
        }
        return objects;
    }

    public static void deleteData() throws SQLException {
        statement.execute("DROP TABLE IF EXISTS sportObjects");
    }

    public static void closeDB() throws SQLException {
        statement.close();
        connection.close();
    }

    public static void firstTask() throws SQLException {
        // Если нужны только области, то после FROM вставить WHERE subject_group LIKE "%область%"
        ResultSet resultSet = statement.executeQuery(
                "SELECT \n" +
                        "    CASE \n" +
                        "        WHEN subject IN ('Москва', 'Московская область') THEN 'Москва и Московская область'\n" +
                        "        ELSE subject\n" +
                        "    END AS subject_group,\n" +
                        "    COUNT(*) AS object_count\n" +
                        "FROM sportObjects\n" +
                        "GROUP BY subject_group\n" +
                        "ORDER BY object_count DESC;");

        HashMap<String, Integer> result = new HashMap<>();
        while (resultSet.next()) {
            String subject = resultSet.getString("subject_group");
            int count = resultSet.getInt("object_count");
            if (subject.isEmpty()) subject = "Не указано";
            result.put(subject, count);
        }
        Graph graph =new Graph(result);
        graph.setVisible(true);
    }

    public static void secondTask() throws SQLException {
        ResultSet resultSet = statement.executeQuery(
                "SELECT AVG(object_count) AS avg_for_subject\n" +
                        "FROM (\n" +
                        "SELECT subject, count(subject) AS object_count\n" +
                        "FROM sportObjects\n" +
                        "GROUP BY subject\n" +
                        ");"
        );

        double avg = resultSet.getDouble("avg_for_subject");
        System.out.println("Среднее кол-во объектов спорта в регионах: " + avg);
    }

    public static void thirdTask() throws SQLException {
        ResultSet resultSet = statement.executeQuery(
                "SELECT subject, count(subject) AS object_count\n" +
                "FROM sportObjects\n" +
                "GROUP BY subject\n" +
                "ORDER BY object_count DESC\n" +
                "LIMIT 3;");

        //HashMap<String, Integer> result = new HashMap<>();
        int topNumber = 0;

        System.out.println("Три региона с самым большим кол-вом объектов спорта:");
        while (resultSet.next()) {
            String subject = resultSet.getString("subject");
            int count = resultSet.getInt("object_count");
            //result.put(subject, count);

            topNumber++;
            System.out.println(String.format("%d) %s - %d", topNumber, subject, count));
        }
    }
}
