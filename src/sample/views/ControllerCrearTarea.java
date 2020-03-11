package sample.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import modelos.Reloj;
import modelos.Tarea;
import sample.logica.Logica;

import java.time.LocalDate;

public class ControllerCrearTarea {
    private Stage stage;
    private Reloj reloj;
    @FXML
    private DatePicker fecha;

    @FXML
    private TextField hora;

    @FXML
    private TextField min;

    @FXML
    private TextField tareaArealizar;

    @FXML
    private Button añadir;


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void añadir(ActionEvent actionEvent) {
        try {

            LocalDate localDate = fecha.getValue();
            int horaTarea = Integer.parseInt(hora.getText());
            int minutoTarea = Integer.parseInt(min.getText());
            String textTarea = tareaArealizar.getText();
            if (horaTarea >= 0 && horaTarea <= 24 && minutoTarea >= 0 && minutoTarea <= 60) {

                    Tarea tarea = new Tarea(localDate, horaTarea, minutoTarea, textTarea, false);
                    Logica.getInstance().añadirTarea(tarea);
                    reloj.registrarTarea(tarea);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tarea creada");
                    alert.setHeaderText(null);
                    alert.setContentText("Se ha creado la tarea");
                    alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tarea no ha sido creada");
                alert.setHeaderText(null);
                alert.setContentText("Revise la fecha y la hora");
                alert.showAndWait();
            }
        } catch (NumberFormatException a) {

        }
    }
    public void pasarReloj(Reloj reloj){
        this.reloj=reloj;
    }
}

