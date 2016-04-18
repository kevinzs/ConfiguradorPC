
package aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ConfiguradorPC extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/VistaConfiguracion.fxml"));
        BorderPane root = (BorderPane) miCargador.load();
        
        Scene scene = new Scene(root, 950, 525);
        stage.setScene(scene);
        stage.setTitle("Configurador de ordenadores");

        stage.show();
        
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
