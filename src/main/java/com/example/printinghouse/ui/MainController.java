package com.example.printinghouse.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView("Publications.fxml");  // default view
    }

    @FXML
    void onNavClick(ActionEvent event) {
        String id = ((javafx.scene.control.Button) event.getSource()).getId();
        loadView(id + ".fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/printinghouse/" + fxmlFile));
            Parent page = loader.load();

            Object controller = loader.getController();

            if (controller instanceof PublicationsController pubCtrl) {
                pubCtrl.setMainController(this);  // подаваме текущия MainController
            }

            contentArea.getChildren().setAll(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadPrintersView() {
        loadView("Printers.fxml");
    }
}
