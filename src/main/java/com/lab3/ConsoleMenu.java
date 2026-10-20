package com.lab3;

import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleMenu {
    private Storage storage = new Storage();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        storage.loadFromFile("data.txt");

        int choice;
        do {
            System.out.println("\n=== МЕНЮ УПРАВЛЕНИЯ (Лаб 3, часть 1) ===");
            System.out.println("1. Показать все элементы (через Iterator) [пункт 10]");
            System.out.println("2. Добавить новый элемент [пункт 9]");
            System.out.println("3. Удалить элемент по ID [пункт 9]");
            System.out.println("4. Сортировать по цене (Лямбда, Comparator) [пункт 11]");
            System.out.println("5. Сортировать по названию [пункт 11]");
            System.out.println("0. Выход");
            System.out.print("Выберите пункт: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Введите число!");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    printItemsIterator();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    removeItem();
                    break;
                case 4:
                    sortByPrice();
                    break;
                case 5:
                    sortByModel();
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        } while (choice != 0);
    }

    private void printItemsIterator() {
        List<Item> list = storage.getItemList();
        if (list.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        System.out.println("\n--- Список устройств ---");
        Iterator<Item> iterator = list.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            System.out.println(item);
        }
    }

    private void addItem() {
        try {
            System.out.print("Введите ID (целое число): ");
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Введите модель (например, Laptop): ");
            String model = scanner.nextLine();

            System.out.print("Введите цену: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Введите объем ОЗУ (ГБ): ");
            int ram = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите дату выпуска (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            Item newItem = new Item(id, model, price, ram, date);
            storage.getItemList().add(newItem);
            storage.getItemMap().put(id, newItem);
            System.out.println("Элемент успешно добавлен!");

        } catch (Exception e) {
            System.out.println("Ошибка при добавлении элемента: " + e.getMessage());
        }
    }

    private void removeItem() {
        System.out.print("Введите ID для удаления: ");
        int id = Integer.parseInt(scanner.nextLine());

        Item removedItem = storage.getItemMap().remove(id);
        if (removedItem != null) {
            storage.getItemList().remove(removedItem);
            System.out.println("Элемент удален: " + removedItem);
        } else {
            System.out.println("Элемент с ID " + id + " не найден.");
        }
    }

    private void sortByPrice() {
        List<Item> list = storage.getItemList();
        list.sort(Comparator.comparingDouble(Item::getPrice));
        System.out.println("Список отсортирован по цене!");
        printItemsIterator();
    }

    private void sortByModel() {
        List<Item> list = storage.getItemList();
        list.sort(Comparator.comparing(Item::getModelName));
        System.out.println("Список отсортирован по названию модели!");
        printItemsIterator();
    }
}