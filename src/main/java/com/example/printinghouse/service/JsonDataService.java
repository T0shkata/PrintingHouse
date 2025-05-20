package com.example.printinghouse.service;

import com.example.printinghouse.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class JsonDataService implements DataService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String basePath;

    public JsonDataService(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public List<Publication> getAllPublications() {
        return readList("publications.json", Publication[].class);
    }

    @Override
    public List<Printer> getAllPrinters() {
        return readList("printers.json", Printer[].class);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return readList("employees.json", Employee[].class);
    }

    private <T> List<T> readList(String filename, Class<T[]> clazz) {
        try (InputStream in = getClass().getResourceAsStream(basePath + "/" + filename)) {
            if (in == null)
                throw new RuntimeException("Resource not found: " + filename);
            T[] array = mapper.readValue(in, clazz);
            return Arrays.asList(array);
        } catch (Exception e) {
            throw new RuntimeException("Error loading JSON " + filename, e);
        }
    }
}
