package com.example.printinghouse.ui;

import com.example.printinghouse.model.Printer;
import com.example.printinghouse.model.Publication;
import com.example.printinghouse.service.DataService;
import com.example.printinghouse.service.JsonDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class PublicationsController implements Initializable {

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

    @FXML private Label amountLabel, publicationLabel, pagesLabel, formatLabel, paperLabel, printerLabel, colourLabel;
    @FXML private Button confirmButton;
    @FXML private VBox formBox;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> publicationBox;
    @FXML private TextField pagesField;
    @FXML private ComboBox<String> formatBox;
    @FXML private ComboBox<String> paperBox;
    @FXML private ComboBox<String> printerBox;
    @FXML private ComboBox<String> colourBox;

    private final DataService ds = new JsonDataService("/json");

    private final Map<String, Double> formatPrice = Map.of(
            "A5", 0.03, "A4", 0.05, "A3", 0.08, "A2", 0.10, "A1", 0.12
    );
    private final Map<String, Double> buyingPrice = Map.of(
            "A5", 0.01, "A4", 0.03, "A3", 0.04, "A2", 0.07, "A1", 0.09
    );
    private final Map<String, Double> paperPrice = Map.of(
            "newspaper", 1.0, "normal", 1.5, "glossy", 1.7
    );

    private List<Printer> allPrinters = new ArrayList<>();
    private PrintersController printersController;
    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Зареждане на всички принтери
        try (InputStream is = getClass().getResourceAsStream("/json/printers.json")) {
            ObjectMapper mapper = new ObjectMapper();
            Printer[] printers = mapper.readValue(is, Printer[].class);
            allPrinters = Arrays.asList(printers);
            updatePrinterBox(); // Първоначално показваме всички
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Зареждане на публикации
        List<Publication> pubs = ds.getAllPublications();
        publicationsTable.getItems().setAll(pubs);

        // Колони
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colPublication.setCellValueFactory(new PropertyValueFactory<>("Publication"));
        colPageCount.setCellValueFactory(new PropertyValueFactory<>("PageCount"));
        colPageFormat.setCellValueFactory(new PropertyValueFactory<>("PageFormat"));
        colPaperType.setCellValueFactory(new PropertyValueFactory<>("PaperType"));
        colPrinter.setCellValueFactory(new PropertyValueFactory<>("Printer"));

        publicationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        publicationsTable.prefWidthProperty().bind(gridPane.widthProperty().multiply(0.75));
        publicationsTable.prefHeightProperty().bind(gridPane.heightProperty().multiply(0.9));

        // Тотали
        totalPubsLabel.setText("Total Publications: " + pubs.size());
        updateTotals();

        // Слушател за colourBox
        colourBox.valueProperty().addListener((obs, oldVal, newVal) -> updatePrinterBox());
    }

    private void updatePrinterBox() {
        printerBox.getItems().clear();

        if (colourBox.getValue() == null) {
            // Без филтър
            for (Printer printer : allPrinters) {
                printerBox.getItems().add("Printer #" + printer.getId());
            }
        } else {
            boolean requiredColour = Boolean.parseBoolean(colourBox.getValue());
            for (Printer printer : allPrinters) {
                if (printer.getType() == requiredColour) {
                    printerBox.getItems().add("Printer #" + printer.getId());
                }
            }
        }
    }

    @FXML
    private void onShowForm(ActionEvent event) {
        amountLabel.setVisible(true); amountField.setVisible(true);
        publicationLabel.setVisible(true); publicationBox.setVisible(true);
        pagesLabel.setVisible(true); pagesField.setVisible(true);
        formatLabel.setVisible(true); formatBox.setVisible(true);
        paperLabel.setVisible(true); paperBox.setVisible(true);
        printerLabel.setVisible(true); printerBox.setVisible(true);
        colourLabel.setVisible(true); colourBox.setVisible(true);
        confirmButton.setVisible(true); colourBox.setVisible(true); colourLabel.setVisible(true);
    }

    @FXML
    private void onConfirmOrder(ActionEvent event) {
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

    private void updateTotals() {
        List<Publication> pubs = publicationsTable.getItems();
        totalPubsLabel.setText("Total Publications: " + pubs.size());

        double totalProfit = pubs.stream().mapToDouble(pub -> {
            double formatCost = formatPrice.getOrDefault(pub.getPageFormat(), 0.0);
            double paperFactor = paperPrice.getOrDefault(pub.getPaperType(), 1.0);
            return pub.getAmount() * pub.getPageCount() * formatCost * paperFactor;
        }).sum();

        totalProfitLabel.setText(String.format("Total Profit: %.2f", totalProfit));
    }

    private void clearFormFields() {
        amountField.clear();
        pagesField.clear();
        publicationBox.setValue(null);
        formatBox.setValue(null);
        paperBox.setValue(null);
        printerBox.setValue(null);
        colourBox.setValue(null);
    }

    public void setPrintersController(PrintersController printersController) {
        this.printersController = printersController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
