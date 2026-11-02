package com.lab3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();

    public static void saveToFile(List<Item> items, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            GSON.toJson(items, writer);
            System.out.println("Данные успешно сохранены в JSON!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Item> readFromFile(String filePath) {
        List<Item> items = new ArrayList<>();
        try (Reader reader = new FileReader(filePath)) {
            Type targetType = new TypeToken<ArrayList<Item>>() {}.getType();
            items = GSON.fromJson(reader, targetType);
            System.out.println("Данные успешно загружены из JSON!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items != null ? items : new ArrayList<>();
    }
}