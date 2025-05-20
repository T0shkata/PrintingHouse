package com.example.printinghouse.ui;

import com.example.printinghouse.model.Publication;
import com.example.printinghouse.model.Printer;
import com.example.printinghouse.model.Employee;
import com.example.printinghouse.service.DataService;
import com.example.printinghouse.service.JsonDataService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private HBox metricsBox;
    @FXML private StackPane revCostChart;
    @FXML private StackPane utilGauge;

    private final DataService ds = new JsonDataService("/json");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load data
        List<Publication> pubs     = ds.getAllPublications();
        List<Printer> printers     = ds.getAllPrinters();
        List<Employee> employees   = ds.getAllEmployees();

        int numPubs           = pubs.size();
        int numPrinters       = printers.size();
        int numEmployees      = employees.size();
        int totalPagesPrinted = printers.stream()
                .mapToInt(Printer::getPagesPrinted)
                .sum();

        // Dynamically load and insert metric cards
        try {
            addMetricCard("Publications",   String.valueOf(numPubs));
            addMetricCard("Printers",       String.valueOf(numPrinters));
            addMetricCard("Employees",      String.valueOf(numEmployees));
            addMetricCard("Pages Printed",  String.valueOf(totalPagesPrinted));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Optionally initialize other components (e.g., charts, tables)
    }

    private void addMetricCard(String title, String value) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/printinghouse/MetricCard.fxml"));
        VBox card = loader.load();
        MetricCardController controller = loader.getController();
        controller.setTitle(title);
        controller.setValue(value);
        metricsBox.getChildren().add(card);
    }

    @FXML
    private void onNewPublication(ActionEvent event) {
        System.out.println("New Publication clicked");
    }

    @FXML
    private void onAddPrinter(ActionEvent event) {
        System.out.println("Add Printer clicked");
    }

    @FXML
    private void onGenerateReport(ActionEvent event) {
        System.out.println("Generate Report clicked");
    }
}
