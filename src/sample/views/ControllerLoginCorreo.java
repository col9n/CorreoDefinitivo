package sample.views;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Reloj;
import sample.logica.Logica;

public class ControllerLoginCorreo {
    private Stage stage;

    @FXML
    private TextField user;

    @FXML
    private PasswordField pass;

    @FXML
    private Button boton;

    @FXML
    private Button register;

    @FXML
    void logear(ActionEvent event) {

       if(Logica.getInstance().compruebaConexion(user.getText(),pass.getText()))
       {
           Logica.getInstance().cargarStore(user.getText(),pass.getText());
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Conexion");
           alert.setHeaderText(null);
           alert.setContentText("Todo esta correcto");
           alert.showAndWait();
           stage.close();





       }
       else {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Conexion");
           alert.setHeaderText(null);
           alert.setContentText("No se pudo conectar con su cuenta revise usuario contraseña  y su configuracion de gmail");
           alert.showAndWait();
       }


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
