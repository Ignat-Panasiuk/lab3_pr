package com.lab3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlDom {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void saveToFile(List<Item> items, String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("items");
            doc.appendChild(rootElement);

            for (Item item : items) {
                Element itemElement = doc.createElement("item");

                itemElement.setAttribute("id", String.valueOf(item.getId()));

                createElement(doc, itemElement, "model", item.getModelName());
                createElement(doc, itemElement, "price", String.valueOf(item.getPrice()));
                createElement(doc, itemElement, "ram", String.valueOf(item.getRamSize()));
                createElement(doc, itemElement, "date", DATE_FORMAT.format(item.getReleaseDate()));

                rootElement.appendChild(itemElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Данные успешно сохранены в XML!");
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    private static void createElement(Document doc, Element parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);

        element.setTextContent(textContent);

        parent.appendChild(element);
    }

    public static List<Item> readFromFile(String filePath) {
        List<Item> items = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            File file = new File(filePath);
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    int id = Integer.parseInt(element.getAttribute("id"));

                    String model = element.getElementsByTagName("model").item(0).getTextContent();
                    double price = Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent());
                    int ram = Integer.parseInt(element.getElementsByTagName("ram").item(0).getTextContent());
                    
                    Date date = DATE_FORMAT.parse(element.getElementsByTagName("date").item(0).getTextContent());

                    Item item = new Item(id, model, price, ram, date);
                    items.add(item);
                }
            }

            System.out.println("Данные успешно загружены из XML!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
