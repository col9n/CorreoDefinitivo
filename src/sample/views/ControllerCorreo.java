package sample.views;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelos.Evento;
import modelos.Reloj;
import modelos.Tarea;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sample.clases.Correo;
import sample.clases.MensajeInforme;
import sample.clases.TreeItemPropio;
import sample.logica.Logica;

import javax.mail.Folder;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerCorreo implements Initializable {
    private Stage stage = new Stage();
    @FXML
    private MenuItem correo;

    @FXML
    private MenuItem cuenta;

    @FXML
    private MenuItem configuracion;

    @FXML
    private MenuItem controlCuentas;

    @FXML
    private Button borrar;

    @FXML
    private Button imprimeCorreo;

    @FXML
    private Button imprimeCarpeta;



    @FXML
    private TableView<Correo> tableViewCorreo;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private WebView webView;


    @FXML
    private Reloj reloj;


    public TreeView<String> getTreeView() {
        return treeView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    /*



    */
        // Store store = Logica.getInstance().cargarStore("eduardocapinjavafx@gmail.com", "eduardojavafx");
        reloj.startSec();


        reloj.addEvento(new Evento() {
            @Override
            public void inicioTarea(Tarea tarea) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Aviso de la tarea creada");
                alert.setHeaderText(null);
                alert.setContentText(tarea.getTextoTarea());
                alert.showAndWait();

            }
        });
        Logica.getInstance().cargarDatos();
        Logica.getInstance().cargarDatosTarea();
        treeView.setRoot(Logica.getInstance().cargarTreeView());


        /**Filtro para selecionar las correos de la tabla*/
        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                Correo correo = (Logica.getInstance().getListaCorreos().get(tableViewCorreo.getSelectionModel().getSelectedIndex()));

                try {
                    webView.getEngine().loadContent(correo.readHtmlContent(correo));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        };
        tableViewCorreo.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseHandler);


        /**Filtro para selecionar las carpetas del treeView*/
        EventHandler<MouseEvent> mouseHandler1 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    tableViewCorreo.getItems().clear();

                    TreeItemPropio treeItem = (TreeItemPropio) treeView.getSelectionModel().getSelectedItem();

                    if (treeItem.getFolder() != null) {
                        if (treeItem != null) {
                            if (treeItem.getFolder().getType() == 3) {
                                Logica.getInstance().cargarListaCorreos(treeItem.getCuenta().getStore(), treeItem.getFolder());
                                tableViewCorreo.setItems(Logica.getInstance().getListaCorreos());
                            }
                        }
                    }

                } catch (Exception e) {


                }
            }

        };
        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseHandler1);



    }


    @FXML
    void nuevoCorreo(ActionEvent event) {


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/escribirCorreo.fxml"));
            Parent root = fxmlLoader.load();
            ControllerEscribirCorreo controllerEscribirCorreo = (ControllerEscribirCorreo) fxmlLoader.getController();
            controllerEscribirCorreo.setStage(stage);
            stage.setTitle("Nuevo Correo");
            stage.setScene(new Scene(root, 600, 400));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();


    }

    @FXML
    void nuevoCuenta(ActionEvent event) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/loginCorreo.fxml"));
            Parent root = fxmlLoader.load();
            ControllerLoginCorreo pantallaConfigCorreo = (ControllerLoginCorreo) fxmlLoader.getController();
            pantallaConfigCorreo.setStage(stage);

            stage.setTitle("Nueva cuenta");
            stage.setScene(new Scene(root, 600, 400));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
        treeView.setRoot(Logica.getInstance().cargarTreeView());
        Logica.getInstance().grabarDatos();

    }


    @FXML
    void configuracion(ActionEvent event) {


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/configuracion.fxml"));
            Parent root = fxmlLoader.load();
            ControllerConfiguracion ControllerConfiguracion = (ControllerConfiguracion) fxmlLoader.getController();
            ControllerConfiguracion.setStage(stage);
            ControllerConfiguracion.pasarReloj(reloj);
            stage.setTitle("Configuracion");
            stage.setScene(new Scene(root, 300, 150));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();


    }

    @FXML
    void controlCuentas(ActionEvent event) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/gestionCuentas.fxml"));
            Parent root = fxmlLoader.load();
            ControllerCuentasCorreo pantallaConfigCorreo = (ControllerCuentasCorreo) fxmlLoader.getController();
            pantallaConfigCorreo.setStage(stage);
            stage.setTitle("Gestion cuenta");
            stage.setScene(new Scene(root, 600, 400));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
        treeView.setRoot(Logica.getInstance().cargarTreeView());
        Logica.getInstance().grabarDatos();

    }

    public void borrarMensaje() {

        Correo correo = (Logica.getInstance().getListaCorreos().get(tableViewCorreo.getSelectionModel().getSelectedIndex()));
        TreeItemPropio treeItem = (TreeItemPropio) treeView.getSelectionModel().getSelectedItem();
        Folder folder = treeItem.getFolder();
        if (correo != null && folder != null)
            Logica.getInstance().borrarCorreo(correo, folder);
    }


    public void correoInbox(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/inbox.fxml"));
            Parent root = fxmlLoader.load();
            ControllerInbox inbox = (ControllerInbox) fxmlLoader.getController();
            inbox.setStage(stage);
            stage.setTitle("Correo Inbox");
            stage.setScene(new Scene(root, 600, 400));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();

    }

    public void crearTareas(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/crearTarea.fxml"));
            Parent root = fxmlLoader.load();
            ControllerCrearTarea crearTarea = (ControllerCrearTarea) fxmlLoader.getController();
            crearTarea.setStage(stage);
            crearTarea.pasarReloj(reloj);
            stage.setTitle("Crear tareas");
            stage.setScene(new Scene(root, 600, 400));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
        Logica.getInstance().grabarDatosTareas();

    }

    public void gestionTareas(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/gestionTareas.fxml"));
            Parent root = fxmlLoader.load();
            ControllerGestionTareas gestionTareas = (ControllerGestionTareas) fxmlLoader.getController();
            gestionTareas.setStage(stage);
            gestionTareas.pasarReloj(reloj);
            stage.setTitle("Gestion tareas");
            stage.setScene(new Scene(root, 600, 400));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();

    }

    public Reloj getReloj() {
        return reloj;
    }

    public void setReloj(Reloj reloj) {
        this.reloj = reloj;
    }


    public void ayuda() {
        ControllerHelp controllerHelp = new ControllerHelp();
        try {
            controllerHelp.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void añadirItemsTableView() {
        Logica.getInstance().getListaMensajeInforme();

    }

    @FXML
    void imprimir(ActionEvent event) {
        File file=Logica.getInstance().getFile();
                    try {
                        List<MensajeInforme> lista = new ArrayList<MensajeInforme>();
                        if (tableViewCorreo.getSelectionModel().getSelectedItem() instanceof Correo) {
                            Correo m = tableViewCorreo.getSelectionModel().getSelectedItem();
                            MensajeInforme email = new MensajeInforme(m.getAsunto(), m.getRemitente(), m.getFecha(), m.getTextoContenido(m));
                            lista.add(email);

                            JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(lista); //lista sería la colección a mostrar. Típicamente saldría de la lógica de nuestra aplicación
                            Map<String, Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
                            JasperPrint print = null;


                            print = JasperFillManager.fillReport("reportes/informeCorreo.jasper", parametros, jr);

                            JasperExportManager.exportReportToPdfFile(print, file.getPath());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Informe");
                            alert.setHeaderText(null);
                            alert.setContentText("Informe Correo generado");
                            alert.showAndWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
    }

    @FXML
    void imprimirCarpeta(ActionEvent event) {
        File file=Logica.getInstance().getFile();
                    try {
                        List<MensajeInforme> lista = new ArrayList<MensajeInforme>();
                        if (treeView.getSelectionModel().getSelectedItem() instanceof TreeItemPropio) {
                            TreeItemPropio treeItem = (TreeItemPropio) treeView.getSelectionModel().getSelectedItem();

                            if (treeItem.getFolder() != null) {
                                if (treeItem != null) {
                                    if (treeItem.getFolder().getType() == 3) {
                                        Logica.getInstance().cargarListaCorreos(treeItem.getCuenta().getStore(), treeItem.getFolder());
                                        for (Correo correo : Logica.getInstance().getListaCorreos()) {


                                            MensajeInforme email = new MensajeInforme(correo.getAsunto(), correo.getRemitente(), correo.getFecha(), correo.getTextoContenido(correo), treeItem.getFolder().getFullName());
                                            lista.add(email);
                                        }
                                    }
                                }
                            }

                            JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(lista); //lista sería la colección a mostrar. Típicamente saldría de la lógica de nuestra aplicación
                            Map<String, Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
                            JasperPrint print = null;


                            print = JasperFillManager.fillReport("reportes/informeCarpeta.jasper", parametros, jr);

                            JasperExportManager.exportReportToPdfFile(print, file.getPath());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Informe");
                            alert.setHeaderText(null);
                            alert.setContentText("Informe Carpeta generado");
                            alert.showAndWait();
                        }
                    } catch (JRException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

    }


    public void cerrarReloj() {
        reloj.terminarReloj();
    }
}
