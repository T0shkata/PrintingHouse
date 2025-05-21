package com.example.printinghouse.ui;

import com.example.printinghouse.model.Printer;
import com.example.printinghouse.model.Publication;
import com.example.printinghouse.service.DataService;
import com.example.printinghouse.service.JsonDataService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PublicationsController implements Initializable
{

    @FXML private GridPane gridPane;

    @FXML private TableView<Publication> publicationsTable;
    @FXML private TableColumn<Publication, Integer> colAmount;
    @FXML private TableColumn<Publication, String> colPublication;
    @FXML private TableColumn<Publication, Integer> colPageCount;
    @FXML private TableColumn<Publication, String> colPageFormat;
    @FXML private TableColumn<Publication, String> colPaperType;
    @FXML private TableColumn<Publication, String> colPrinter;

    @FXML private Label totalPubsLabel;
    @FXML private Label totalProfitLabel;

    @FXML private Label amountLabel, publicationLabel, pagesLabel, formatLabel, paperLabel, printerLabel;
    @FXML private Button confirmButton;
    @FXML private VBox formBox;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> publicationBox;
    @FXML private TextField pagesField;
    @FXML private ComboBox<String> formatBox;
    @FXML private ComboBox<String> paperBox;
    @FXML private ComboBox<String> printerBox;

    private final DataService ds = new JsonDataService("/json");
    private final Map<String, Double> formatPrice = Map.of(
            "A5", 0.03,
            "A4", 0.05,
            "A3", 0.08,
            "A2", 0.10,
            "A1", 0.12
    );

    private final Map<String, Double> paperPrice = Map.of(
            "normal", 1.0,
            "premium", 1.5,
            "glossy", 1.7
    );

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // Load data
        List<Publication> pubs = ds.getAllPublications();
        List<Printer> printers = ds.getAllPrinters();
        printerBox.getItems().addAll(
                printers.stream()
                        .map(p -> String.valueOf(p.getId()))
                        .toList()
        );

        // Table column bindings
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colPublication.setCellValueFactory(new PropertyValueFactory<>("Publication"));
        colPageCount.setCellValueFactory(new PropertyValueFactory<>("PageCount"));
        colPageFormat.setCellValueFactory(new PropertyValueFactory<>("PageFormat"));
        colPaperType.setCellValueFactory(new PropertyValueFactory<>("PaperType"));
        colPrinter.setCellValueFactory(new PropertyValueFactory<>("Printer"));

        publicationsTable.getItems().setAll(pubs);
        publicationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Responsive table dimensions
        publicationsTable.prefWidthProperty().bind(gridPane.widthProperty().multiply(0.75));
        publicationsTable.maxWidthProperty().bind(gridPane.widthProperty().multiply(0.75));
        publicationsTable.prefHeightProperty().bind(gridPane.heightProperty().multiply(0.9));
        publicationsTable.maxHeightProperty().bind(gridPane.heightProperty().multiply(0.9));

        // Update totals
        totalPubsLabel.setText("Total Publications: " + pubs.size());

        double totalPages = pubs.stream()
                .mapToInt(pub -> pub.getAmount() * pub.getPageCount())
                .sum();

        double totalProfit = pubs.stream()
                .mapToDouble(pub -> {
                    double formatCost = formatPrice.getOrDefault(pub.getPageFormat(), 0.0);
                    double paperFactor = paperPrice.getOrDefault(pub.getPaperType(), 1.0);
                    return pub.getAmount() * pub.getPageCount() * formatCost * paperFactor;
                }).sum();

        if (totalPages > 2000) {
            totalProfit *= 0.9; // 10% отстъпка
        }

        totalProfitLabel.setText(String.format("Total Profit: %.2f", totalProfit));


        totalProfitLabel.setText(String.format("Total Profit: %.2f", totalProfit));
    }

    @FXML
    private void onNewPublication(ActionEvent event)
    {
        System.out.println("New Publication clicked");
    }

    @FXML
    private void onAddPrinter(ActionEvent event)
    {
        System.out.println("Add Printer clicked");
    }

    @FXML
    private void onGenerateReport(ActionEvent event)
    {
        System.out.println("Generate Report clicked");
    }

    @FXML
    private void onShowForm(ActionEvent event)
    {
        amountLabel.setVisible(true);
        amountField.setVisible(true);

        publicationLabel.setVisible(true);
        publicationBox.setVisible(true);

        pagesLabel.setVisible(true);
        pagesField.setVisible(true);

        formatLabel.setVisible(true);
        formatBox.setVisible(true);

        paperLabel.setVisible(true);
        paperBox.setVisible(true);

        printerLabel.setVisible(true);
        printerBox.setVisible(true);

        confirmButton.setVisible(true);
    }

    @FXML
    private void onConfirmOrder(ActionEvent event)
    {
        if (amountField.getText().isBlank() || pagesField.getText().isBlank() ||
                publicationBox.getValue() == null || formatBox.getValue() == null ||
                paperBox.getValue() == null || printerBox.getValue() == null) {

            System.out.println("Please, fill all the fields.");
            return;
        }

        try {
            int amount = Integer.parseInt(amountField.getText());
            int pages = Integer.parseInt(pagesField.getText());

            Publication newPub = new Publication();
            newPub.setAmount(amount);
            newPub.setPageCount(pages);
            newPub.setPublication(publicationBox.getValue());
            newPub.setPageFormat(formatBox.getValue());
            newPub.setPaperType(paperBox.getValue());
            newPub.setPrinter(printerBox.getValue());

            ds.addPublication(newPub);
            publicationsTable.getItems().add(newPub);
            updateTotals();

            clearFormFields();

        } catch (NumberFormatException e) {
            System.out.println("Amount or page count field is not a number.");
        }
    }

    private void updateTotals()
    {
        List<Publication> pubs = publicationsTable.getItems();
        totalPubsLabel.setText("Total Publications: " + pubs.size());

        double totalProfit = pubs.stream().mapToDouble(pub -> {
            double formatCost = formatPrice.getOrDefault(pub.getPageFormat(), 0.0);
            double paperFactor = paperPrice.getOrDefault(pub.getPaperType(), 1.0);
            return pub.getAmount() * pub.getPageCount() * formatCost * paperFactor;
        }).sum();
        totalProfitLabel.setText(String.format("Total Profit: %.2f", totalProfit));
    }

    private void clearFormFields()
    {
        amountField.clear();
        pagesField.clear();
        publicationBox.setValue(null);
        formatBox.setValue(null);
        paperBox.setValue(null);
        printerBox.setValue(null);
    }
    // Optional method to reuse if re-adding cards
//    private void addMetricCard(String title, String value) throws Exception
//    {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/printinghouse/MetricCard.fxml"));
//        VBox card = loader.load();
//        MetricCardController controller = loader.getController();
//        controller.setTitle(title);
//        controller.setValue(value);
//        // metricsBox.getChildren().add(card); // Uncomment if you re-add metric cards
//    }
}
