package sample.views;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import sample.clases.CuentaCorreo;
import sample.logica.Logica;

import javax.mail.Store;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEscribirCorreo implements Initializable {
    private Stage stage;

    @FXML
    private Button enviarBoton;

    @FXML
    private TextField to;

    @FXML
    private TextField asunto;

    @FXML
    private HTMLEditor mensaje;

    @FXML
    private ComboBox<CuentaCorreo> combobox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        combobox.getItems().addAll((Logica.getInstance().getListaCuentas()));
    }

    @FXML
    void enviar(ActionEvent event) {
        CuentaCorreo cuenta=combobox.getSelectionModel().getSelectedItem();
        String para = to.getText();
        String subject=asunto.getText();
        String cuerpo = mensaje.getHtmlText();
        if(!cuerpo.isEmpty() && !subject.isEmpty() && !para.isEmpty() &&cuenta!=null)
        {
            if(Logica.getInstance().enviarCorreo(cuenta,para,"",subject,cuerpo)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Enviar mensaje");
                alert.setHeaderText(null);
                alert.setContentText("El mensaje fue enviado");
                alert.showAndWait();
                stage.close();
            }


        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enviar mensaje");
            alert.setHeaderText(null);
            alert.setContentText("Revisa los parametros del mensaje");
            alert.showAndWait();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
