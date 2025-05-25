package com.example.printinghouse.service;

import com.example.printinghouse.model.*;
import java.util.List;

public interface DataService {
    List<Publication> getAllPublications();
    List<Printer>     getAllPrinters();
    List<Employee>    getAllEmployees();
    void addPublication(Publication publication);
    void updatePrinterWithPublication(Publication pub);
}
