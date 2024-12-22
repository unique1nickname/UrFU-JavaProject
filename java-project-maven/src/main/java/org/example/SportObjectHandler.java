package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class SportObjectHandler {
    public static ArrayList<SportObject> readFile(String path) {
        ArrayList<SportObject> objects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path, Charset.forName("Windows-1251")))) {
            String line = reader.readLine(); // скип заголовка

            while ((line = reader.readLine()) != null) {

                // Из всех csv файлов во всех вариантах конкретно этот - сломан.
                // Другие варианты нормально разделяются, но в этом есть строки по типу "текст "текст "текст" текст" <-- последней " не хватает.
                // Поэтому разделение сделано вот так:

                String[] idAndOther = line.split(",", 2);
                int id = Integer.parseInt(idAndOther[0].trim());
                String[] fields = idAndOther[1].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                String date = fields[fields.length - 1].trim();
                date = date.isEmpty() ? date : date.substring(1, date.length() - 1);

                String address = fields[fields.length - 2].trim();
                address = address.isEmpty() ? address : address.substring(1, address.length() - 1);

                String subjectRF = fields[fields.length - 3].trim();
                subjectRF = subjectRF.isEmpty() ? subjectRF : subjectRF.substring(1, subjectRF.length() - 1);

                // Там почему-то есть и Москва и г. Москва. Если так и должно быть - удалить строку ниже
                if (subjectRF.equals("г. Москва")) subjectRF = "Москва";

                StringBuilder nameSB = new StringBuilder();
                for (int i = 0; i < fields.length - 3; i++) {
                    nameSB.append(fields[i]);
                }
                String name = nameSB.toString().trim();
                name = name.isEmpty() ? name : name.substring(1, name.length() - 1);

                objects.add(new SportObject(id, name, subjectRF, address, date));
            }
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
        return objects;
    }
}
