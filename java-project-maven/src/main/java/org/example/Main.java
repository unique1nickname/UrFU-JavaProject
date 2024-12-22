package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        var sportObjects = SportObjectHandler.readFile("Объекты спорта.csv");

        /* Вывод всех данных из полученного списка
        System.out.println("Вывод всех данных");
        for (var sportObject : sportObjects) {
            System.out.println(sportObject);
        }
        System.out.println("\n");
         */

        //ArrayList<SportObject> newObj;
        try {
            Database.connectDB();
            Database.deleteData();
            Database.createTable();
            System.out.println("Данные заносятся в базу данных...");
            Database.inputData(sportObjects);
            //newObj = Database.readData();

            Database.firstTask();
            System.out.println(" ");
            Database.secondTask();
            System.out.println(" ");
            Database.thirdTask();


            Database.closeDB();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}