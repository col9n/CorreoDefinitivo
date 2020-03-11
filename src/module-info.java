module correo {
    requires java.mail;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires java.sql;
    requires commons.email;
    requires Reloj;
    requires org.docgene.help.jfx;
    requires jasperreports;
    requires org.jsoup;

    exports sample;
    exports sample.views;
    exports sample.clases;
    exports sample.logica;

    opens sample.views to javafx.fxml;
}