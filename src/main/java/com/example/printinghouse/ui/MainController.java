package com.example.printinghouse.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadView("Dashboard.fxml");  // show Dashboard by default
    }

    @FXML
    void onNavClick(ActionEvent event)
    {
        String id = ((javafx.scene.control.Button) event.getSource()).getId();
        loadView(id + ".fxml");
    }

    private void loadView(String fxmlFile)
    {
        try
        {
            Parent page = FXMLLoader.load(
                    getClass().getResource("/com/example/printinghouse/" + fxmlFile)
            );
            contentArea.getChildren().setAll(page);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
