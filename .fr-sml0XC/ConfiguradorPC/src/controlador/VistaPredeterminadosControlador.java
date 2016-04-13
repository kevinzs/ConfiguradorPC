
package controlador;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import modelo.ListPcWrapper;

public class VistaPredeterminadosControlador implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBox;
    private ObservableList<String> nombresPC;

    private VistaConfiguracionControlador controlador;
    
    private ListPcWrapper listaPC;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombresPC = FXCollections.observableArrayList();
        try {
            File file = new File("predeterminados.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ListPcWrapper.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            listaPC = (ListPcWrapper) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        
        for(int i = 0; i < listaPC.getPCList().size(); i++)
            nombresPC.add(listaPC.getPCList().get(i).getNombre());
        
        choiceBox.setItems(nombresPC);
        choiceBox.getSelectionModel().selectFirst();
    }    

    @FXML
    private void aceptarAction(ActionEvent event) {
        controlador.setConfiguracion(listaPC.getPCList().get(choiceBox.getSelectionModel().getSelectedIndex()));
        
        Node nodo = (Node) event.getSource();
        nodo.getScene().getWindow().hide();
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
}
