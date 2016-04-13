
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import modelo.Componente;

public class VistaModificarControlador implements Initializable {

    private VistaConfiguracionControlador controlador;
    
    @FXML private TextField cantidad;
   
    private Componente componente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void aceptarAction(ActionEvent event) {
        if(!cantidad.getText().isEmpty() && isInteger(cantidad.getText())){
            controlador.modificarComponente(componente, new Componente(componente.getDescripcion(),
                componente.getPrecio(), componente.getStock(), componente.getCategoria(),
                Integer.parseInt(cantidad.getText())));
            //Cerramos la ventana
            Node nodo = (Node) event.getSource();
            nodo.getScene().getWindow().hide();
        } else
            alerta("Introduce una cantidad válida.");
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        //Cerramos la ventana
        Node nodo = (Node) event.getSource();
        nodo.getScene().getWindow().hide();
    }
    
    public void setControlador(VistaConfiguracionControlador controlador){
        this.controlador = controlador;
    }
    
    public void setComponente(Componente componente){
        this.componente = componente;
    }
    
    public void alerta(String mensaje){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
   
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException | NullPointerException e) { 
            return false; 
        }
        return true;
    }
}
