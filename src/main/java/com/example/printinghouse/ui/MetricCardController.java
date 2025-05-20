package com.example.printinghouse.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MetricCardController
{
    @FXML private Label titleLabel;
    @FXML private Label valueLabel;

    public void setTitle(String title)
    {
        titleLabel.setText(title);
    }

    public void setValue(String value)
    {
        valueLabel.setText(value);
    }
}

