package com.example.printinghouse.ui;

import com.example.printinghouse.model.Employee;
import com.example.printinghouse.service.DataService;
import com.example.printinghouse.service.JsonDataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {

    @FXML
    private VBox employeeList;

    private final DataService dataService = new JsonDataService("/json");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Employee> employees = dataService.getAllEmployees();

        for (Employee employee : employees) {
            VBox card = createEmployeeCard(employee);
            employeeList.getChildren().add(card);
        }
    }

    private VBox createEmployeeCard(Employee employee) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 6, 0, 0, 4);");

        Label name = new Label("Name: " + employee.getName());
        Label position = new Label("Position: " + employee.getPosition());
        Label salary = new Label("Salary: $" + employee.getSalary());

        name.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        position.setStyle("-fx-font-size: 14;");
        salary.setStyle("-fx-font-size: 14;");

        card.getChildren().addAll(name, position, salary);
        return card;
    }
}
