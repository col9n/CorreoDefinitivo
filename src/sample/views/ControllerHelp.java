package sample.views;


import javafx.stage.Stage;
import org.docgene.help.JavaHelpFactory;
import org.docgene.help.gui.jfx.JFXHelpContentViewer;

import java.io.File;
import java.net.URL;

public class ControllerHelp {
    private JFXHelpContentViewer viewer;

    private void initializeHelp(Stage stage) {
        try {
            File file = new File("help/articles.zip");
            URL url = file.toURI().toURL();
            JavaHelpFactory factory = new JavaHelpFactory(url);
            factory.create();
            viewer = new JFXHelpContentViewer();
            factory.install(viewer);
            viewer.getHelpWindow(stage, "Help Content", 900, 700);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void start(Stage primaryStage) throws Exception {
        initializeHelp(primaryStage);
        viewer.showHelpDialog(0, 0);

    }

}