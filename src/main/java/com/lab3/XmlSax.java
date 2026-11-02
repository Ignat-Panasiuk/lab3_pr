package com.lab3;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlSax {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static List<Item> readFromFile(String filePath) {
        List<Item> items = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            ItemHandler handler = new ItemHandler();
            saxParser.parse(new File(filePath), handler);
            
            items = handler.getItems();
            System.out.println("Данные успешно загружены через SAX парсер!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    private static class ItemHandler extends DefaultHandler {
        private List<Item> items = new ArrayList<>();
        private Item currentItem;
        private StringBuilder currentText = new StringBuilder();
        private int id;
        private String model;
        private double price;
        private int ram;
        private Date date;

        public List<Item> getItems() {
            return items;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentText.setLength(0);
            if (qName.equalsIgnoreCase("item")) {
                id = Integer.parseInt(attributes.getValue("id"));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            currentText.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            try {
                if (qName.equalsIgnoreCase("model")) {
                    model = currentText.toString();
                } else if (qName.equalsIgnoreCase("price")) {
                    price = Double.parseDouble(currentText.toString());
                } else if (qName.equalsIgnoreCase("ram")) {
                    ram = Integer.parseInt(currentText.toString());
                } else if (qName.equalsIgnoreCase("date")) {
                    date = DATE_FORMAT.parse(currentText.toString());
                } else if (qName.equalsIgnoreCase("item")) {
                    currentItem = new Item(id, model, price, ram, date);
                    items.add(currentItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}