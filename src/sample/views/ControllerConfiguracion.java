package sample.views;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Reloj;


public class ControllerConfiguracion {
    private Stage stage;
    private Reloj reloj;


    @FXML
    private TextField user;

    @FXML
    private PasswordField pass;

    @FXML
    private Button caspian;

    @FXML
    private Button modena;

    @FXML
    public void cargarCaspian(ActionEvent event ) {

        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Enviar mensaje");
        alert.setHeaderText(null);
        alert.setContentText("Has puesto el estilo Caspian" +"");
        alert.showAndWait();
    }
    @FXML
    public void cargarModena(ActionEvent event ) {

        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Enviar mensaje");
        alert.setHeaderText(null);
        alert.setContentText("Has puesto el estilo Modena");
        alert.showAndWait();
        stage.close();
    }

    @FXML
    public void cambiarFormato(ActionEvent event ) {

       if(reloj.getFormato24H()){
           reloj.setFormato24H(false);

       }
       else
           reloj.setFormato24H(true);
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reloj");
        alert.setHeaderText(null);
        if(reloj.getFormato24H())
            alert.setContentText("Has puesto el reloj en formato 24h");
        else
            alert.setContentText("Has puesto el reloj en formato 12h");
        alert.showAndWait();
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void pasarReloj(Reloj reloj) {
        this.reloj=reloj;
    }
}
