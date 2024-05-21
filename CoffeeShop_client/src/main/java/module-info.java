module com.example.project_client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires lombok;
    requires atlantafx.styles;
    requires org.kordamp.ikonli.material2;
    requires itextpdf;
    requires atlantafx.base;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.desktop;

    opens com.example.project_client to javafx.fxml;
    exports com.example.project_client;
    opens com.example.project_client.model;
    exports com.example.project_client.model;
    exports com.example.project_client.view.controller.Order;
    opens com.example.project_client.view.controller.Order to javafx.fxml;
    exports com.example.project_client.view.controller.Revenue;
    opens com.example.project_client.view.controller.Revenue to javafx.fxml;
    exports com.example.project_client.view.controller.Customer;
    opens com.example.project_client.view.controller.Customer to javafx.fxml;

    exports com.example.project_client.view.controller.Product;
    opens com.example.project_client.view.controller.Product to javafx.fxml;
    exports com.example.project_client.view.controller.Staff;
    opens com.example.project_client.view.controller.Staff to javafx.fxml;
}