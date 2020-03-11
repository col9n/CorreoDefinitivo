package sample.views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.clases.Correo;
import sample.clases.TreeItemPropio;
import sample.logica.Logica;

import javax.mail.Folder;
import java.net.URL;
import java.util.ResourceBundle;

public class  ControllerInbox implements Initializable {
    private Stage stage;
    @FXML
    private ComboBox<Correo> comboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
        comboBox.setItems(Logica.getInstance().cargarCorreoInbox());
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void dialogo(ActionEvent event) {
        if (comboBox.getSelectionModel() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Monstrar remitente");
            alert.setHeaderText(null);
            alert.setContentText(comboBox.getSelectionModel().getSelectedItem().getAsunto().toString());
            alert.showAndWait();
        }
    }
}
