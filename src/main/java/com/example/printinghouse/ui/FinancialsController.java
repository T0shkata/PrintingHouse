package com.example.printinghouse.ui;

import com.example.printinghouse.model.Employee;
import com.example.printinghouse.model.Publication;
import com.example.printinghouse.service.DataService;
import com.example.printinghouse.service.JsonDataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FinancialsController implements Initializable {

    @FXML private VBox orderList;
    @FXML private Button paySalariesBtn;
    @FXML private VBox salaryCard;
    @FXML private Label incomeLabel;
    @FXML private Label expensesLabel;
    @FXML private Label profitLabel;

    private final DataService ds = new JsonDataService("/json");
    private double income = 0;
    private double expenses = 0;

    private final Map<String, Double> formatPrice = Map.of(
            "A1", 0.6,
            "A2", 0.4,
            "A3", 0.2,
            "A4", 0.15,
            "A5", 0.1
    );

    private final Map<String, Double> paperPrice = Map.of(
            "normal", 1.0,
            "glossy", 1.5
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Publication> publications = ds.getAllPublications();

        int totalPages = publications.stream()
                .mapToInt(pub -> pub.getAmount() * pub.getPageCount())
                .sum();

        for (Publication pub : publications) {
            double formatCost = formatPrice.getOrDefault(pub.getPageFormat(), 0.0);
            double paperFactor = paperPrice.getOrDefault(pub.getPaperType(), 1.0);

            double orderProfit = pub.getAmount() * pub.getPageCount() * formatCost * paperFactor;
            income += orderProfit;

            VBox card = new VBox(5);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 6, 0, 0, 4);");

            Label title = new Label("Publication: " + pub.getPublication());
            Label amount = new Label("Copies: " + pub.getAmount());
            Label profitLbl = new Label("Profit: $" + String.format("%.2f", orderProfit));

            title.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            amount.setStyle("-fx-font-size: 13;");
            profitLbl.setStyle("-fx-font-size: 13;");

            card.getChildren().addAll(title, amount, profitLbl);
            orderList.getChildren().add(card);
        }

        // Apply 10% discount if over 2000 pages
        if (totalPages > 2000) {
            income *= 0.9;
        }

        updateProfitDisplay();
    }

    @FXML
    public void onPaySalaries() {
        List<Employee> employees = ds.getAllEmployees();
        double totalSalaries = employees.stream().mapToDouble(Employee::getSalary).sum();
        expenses += totalSalaries;

        salaryCard.setVisible(true);
        salaryCard.getChildren().clear(); // <--- това липсваше

        Label header = new Label("Salaries Paid: $" + totalSalaries);
        header.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        salaryCard.getChildren().add(header);

        for (Employee emp : employees) {
            Label lbl = new Label(emp.getName() + ": $" + emp.getSalary());
            salaryCard.getChildren().add(lbl);
        }

        updateProfitDisplay();
    }

    private void updateProfitDisplay() {
        incomeLabel.setText(String.format("$%.2f", income));
        expensesLabel.setText(String.format("$%.2f", expenses));
        profitLabel.setText(String.format("$%.2f", income - expenses));
    }
}
