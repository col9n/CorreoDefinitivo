package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.views.ControllerCorreo;
import sample.views.ControllerEscribirCorreo;
import sample.views.ControllerLoginCorreo;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

       FXMLLoader fxmlLoader = new  FXMLLoader(getClass().getResource("views/correo.fxml"));
       Parent root = fxmlLoader.load();
       ControllerCorreo controllerCorreo = (ControllerCorreo)fxmlLoader.getController();
       primaryStage.show();
       // Scene scene = new Scene(root,800,800);
        primaryStage.setScene(new Scene(root,1000,700));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                controllerCorreo.cerrarReloj();
            }
        });
    }






    public static void main(String[] args) {
        launch(args);
    }
}
