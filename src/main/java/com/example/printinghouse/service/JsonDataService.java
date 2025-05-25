package com.example.printinghouse.service;

import com.example.printinghouse.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonDataService implements DataService
{
    private final ObjectMapper mapper = new ObjectMapper();
    private final String basePath;

    public JsonDataService(String basePath)
    {
        this.basePath = basePath;
    }

    @Override
    public List<Publication> getAllPublications()
    {
        return readList("publications.json", Publication[].class);
    }

    @Override
    public List<Printer> getAllPrinters()
    {
        return readList("printers.json", Printer[].class);
    }

    @Override
    public List<Employee> getAllEmployees()
    {
        return readList("employees.json", Employee[].class);
    }

    @Override
    public void addPublication(Publication newPub)
    {
        List<Publication> list = getAllPublications();
        list.add(newPub);

        ObjectMapper mapper = new ObjectMapper();
        try (Writer writer = new FileWriter("src/main/resources/json/publications.json"))
        {
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePrinterWithPublication(Publication pub)
    {
        List<Printer> printers = getAllPrinters();

        for (Printer printer : printers) {
            if (String.valueOf(printer.getId()).equals(pub.getPrinter())) {
                int printedPages = pub.getAmount() * pub.getPageCount();
                printer.setPagesPrinted(printer.getPagesPrinted() + printedPages);

                String title = pub.getPublication();
                boolean found = false;

                for (PrintedPublication pp : printer.getPrintedPublications()) {
                    if (pp.getTitle().equalsIgnoreCase(title)) {
                        pp.setCopies(pp.getCopies() + pub.getAmount());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    PrintedPublication newEntry = new PrintedPublication();
                    newEntry.setTitle(title);
                    newEntry.setCopies(pub.getAmount());
                    printer.getPrintedPublications().add(newEntry);
                }

                break; // found the printer, no need to continue
            }
        }

        writeList("printers.json", printers);
    }

    private <T> List<T> readList(String filename, Class<T[]> clazz)
    {
        try (InputStream in = getClass().getResourceAsStream(basePath + "/" + filename))
        {
            if (in == null)
                throw new RuntimeException("Resource not found: " + filename);
            T[] array = mapper.readValue(in, clazz);
            return new ArrayList<>(Arrays.asList(array));
        } catch (Exception e) {
            throw new RuntimeException("Error loading JSON " + filename, e);
        }
    }

    private <T> void writeList(String relativePath, List<T> list) {
        try (Writer writer = new FileWriter("src/main/resource/" + basePath + "/" + relativePath))
        {
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer, list);
            System.out.println("✔ Successfully wrote to: " + relativePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON to " + relativePath, e);
        }
    }
}
