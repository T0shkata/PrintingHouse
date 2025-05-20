module com.example.printinghouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    // Needed for FXML loading of controllers
    opens com.example.printinghouse to javafx.fxml;
    opens com.example.printinghouse.ui to javafx.fxml;

    // ✅ Needed so Jackson can use reflection to access model constructors
    opens com.example.printinghouse.model to com.fasterxml.jackson.databind;

    // Optional: if you want other modules to use these packages
    exports com.example.printinghouse;
    exports com.example.printinghouse.ui;
    exports com.example.printinghouse.model;
}
