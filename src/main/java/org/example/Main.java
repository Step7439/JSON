package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        String JSONCSV = "StringJson.json";

        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json, JSONCSV);

        List<Employee> list2 = parseXML("data.xml");
        String JSONXML = "StringJson2.json";
        String writeStr = listToJson(list2);
        writeString(writeStr, JSONXML);

    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            List<Employee> list = csv.parse();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String listGson = gson.toJson(list, listType);

        return listGson;
    }

    private static List<Employee> parseXML(String str) throws ParserConfigurationException, IOException, SAXException {
        List listXML = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("data.xml"));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        try {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node1 = nodeList.item(i);
                if (Node.ELEMENT_NODE == node1.getNodeType()) {

                    Element employee1 = (Element) node1;
                    NodeList ElIdID = employee1.getElementsByTagName("id");
                    Element ElementID = (Element) ElIdID.item(0);
                    NodeList ValueID = ElementID.getChildNodes();
                    String iD = ValueID.item(0).getNodeValue();

                    Element employee2 = (Element) node1;
                    NodeList ElIdFirstName = employee2.getElementsByTagName("firstName");
                    Element ElementFirstName = (Element) ElIdFirstName.item(0);
                    NodeList ValueFirstName = ElementFirstName.getChildNodes();
                    String firstName = ValueFirstName.item(0).getNodeValue();

                    Element employee3 = (Element) node1;
                    NodeList ElLastNameLastName = employee3.getElementsByTagName("lastName");
                    Element ElementLastName = (Element) ElLastNameLastName.item(0);
                    NodeList ValueLastName = ElementLastName.getChildNodes();
                    String lastName = ValueLastName.item(0).getNodeValue();

                    Element employee4 = (Element) node1;
                    NodeList Country = employee4.getElementsByTagName("country");
                    Element fourCountry = (Element) Country.item(0);
                    NodeList fourValue = fourCountry.getChildNodes();
                    String country = fourValue.item(0).getNodeValue();

                    Element employee5 = (Element) node1;
                    NodeList Age = employee5.getElementsByTagName("age");
                    Element fiveAge = (Element) Age.item(0);
                    NodeList fiveValue = fiveAge.getChildNodes();
                    String age = fiveValue.item(0).getNodeValue();

                    Employee employee = new Employee(Long.parseLong(iD), firstName, lastName, country, Integer.parseInt(age));
                    listXML.add(employee);
                }
            }
        } catch (DOMException e) {
            throw new RuntimeException(e);
        }
        return listXML;
    }

    public static void writeString(String json, String jsonFile) {
        File newFile = new File(jsonFile);
        if (newFile.exists() && !newFile.isDirectory()) {
            System.out.println("Обновлен файл: " + jsonFile);
        } else {
            System.out.println("Создон файл: " + jsonFile);
        }

        try (FileWriter writer = new FileWriter(newFile, false);) {
            writer.write(json);
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
