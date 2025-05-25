package com.example.printinghouse.ui;

import com.example.printinghouse.model.Paper;
import com.example.printinghouse.model.Printer;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.util.List;

public class PrintersController
{

    @FXML private VBox printerCardsBox;
    @FXML private TextField normalA1, normalA2, normalA3, normalA4, normalA5;
    @FXML private TextField glossyA1, glossyA2, glossyA3, glossyA4, glossyA5;
    @FXML private TextField newspaperA1, newspaperA2, newspaperA3, newspaperA4, newspaperA5;

    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize()
    {
        refreshPrintersView();
        loadPaperStatus();
    }

    private void loadPrinters()
    {
        try (InputStream is = getClass().getResourceAsStream("/json/printers.json"))
        {
            printerCardsBox.getChildren().clear();

            List<Printer> printers = List.of(mapper.readValue(is, Printer[].class));
            for (Printer p : printers)
            {
                VBox card = new VBox(8);
                card.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #cccccc;" +
                    "-fx-border-radius: 8;" +
                    "-fx-background-radius: 8;" +
                    "-fx-padding: 12;" +
                    "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 6, 0, 0, 2);"
                );
                card.setMinWidth(380);
                card.setMaxWidth(Double.MAX_VALUE);

                Text title = new Text("🖨 Printer #" + p.getId());
                title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

                Text colour = new Text("Colour:" + p.getType());
                Text pages = new Text("Printed Pages: " + p.getPagesPrinted());
                Text speed = new Text("Speed: " + p.getPagesPerMinute() + " ppm");
                Text papers = new Text("Papers Count: " + p.getPapersCount());

                card.getChildren().addAll(title, colour, pages, speed, papers);
                printerCardsBox.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPaperStatus()
    {
        try (InputStream is = getClass().getResourceAsStream("/json/paper.json")) {
            Paper paper = mapper.readValue(is, Paper.class);

            normalA1.setText(paper.getNormal().get("A1") + "");
            normalA2.setText(paper.getNormal().get("A2") + "");
            normalA3.setText(paper.getNormal().get("A3") + "");
            normalA4.setText(paper.getNormal().get("A4") + "");
            normalA5.setText(paper.getNormal().get("A5") + "");

            glossyA1.setText(paper.getGlossy().get("A1") + "");
            glossyA2.setText(paper.getGlossy().get("A2") + "");
            glossyA3.setText(paper.getGlossy().get("A3") + "");
            glossyA4.setText(paper.getGlossy().get("A4") + "");
            glossyA5.setText(paper.getGlossy().get("A5") + "");

            newspaperA1.setText(paper.getNewspaper().get("A1") + "");
            newspaperA2.setText(paper.getNewspaper().get("A2") + "");
            newspaperA3.setText(paper.getNewspaper().get("A3") + "");
            newspaperA4.setText(paper.getNewspaper().get("A4") + "");
            newspaperA5.setText(paper.getNewspaper().get("A5") + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshPrintersView() {
        printerCardsBox.getChildren().clear();
        loadPrinters();
    }

}
