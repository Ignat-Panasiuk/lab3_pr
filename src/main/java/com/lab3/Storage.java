package com.lab3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class Storage {
    private List<Item> itemList = new ArrayList<>();
    private Map<Integer, Item> itemMap = new HashMap<>();

public void loadFromFile(String fileName) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            try {
                String[] parts = line.split(";");
                if (parts.length != 5) {
                    throw new IllegalArgumentException("Неверный формат строки (ожидалось 5 полей)");
                }

                int id = Integer.parseInt(parts[0].trim());
                String modelName = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                int ramSize = Integer.parseInt(parts[3].trim());
                Date releaseDate = dateFormat.parse(parts[4].trim());

                Item item = new Item(id, modelName, price, ramSize, releaseDate);
                
                itemList.add(item);
                itemMap.put(id, item);

                } catch (Exception e) {
                    System.err.println("[LOG ERROR] Ошибка в строке " + lineNumber + ": \"" + line + "\" -> " + e.getMessage());
                }
            }
            System.out.println("Данные успешно загружены. Всего корректных объектов: " + itemList.size());

        } catch (Exception e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public Map<Integer, Item> getItemMap() {
        return itemMap;
    }
}