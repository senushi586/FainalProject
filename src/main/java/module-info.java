module lk.ijse.project {
    requires com.github.librepdf.openpdf;
    requires com.jfoenix;
    requires jasperreports;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires static lombok;
    requires mail;

    opens lk.ijse.project.coffeeshop;
    exports lk.ijse.project.coffeeshop;
    opens lk.ijse.project.coffeeshop.controller to javafx.fxml;
    exports lk.ijse.project.coffeeshop.controller;
    opens lk.ijse.project.coffeeshop.dto.tm;
    exports lk.ijse.project.coffeeshop.dto.tm;


}