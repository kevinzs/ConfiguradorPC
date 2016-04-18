 
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import modelo.Componente;
import modelo.ListPcWrapper;
import modelo.PC;


public class VistaConfiguracionControlador implements Initializable {

    private PC configuracion;
    private ListPcWrapper pcList; //Contiene todos los pc's del XML
    
    public boolean predeterminado = false;
    
    @FXML private TableView<Componente> tabla;
    @FXML private TableColumn<Componente, String> columnaNombre;
    @FXML private TableColumn<Componente, String> columnaCategoria;
    @FXML private TableColumn<Componente, Double> columnaPrecio;
    @FXML private TableColumn<Componente, Integer> columnaCantidad;
    @FXML private TableColumn<Componente, Integer> columnaIVA;
    @FXML private TableColumn<Componente, Double> columnaTotal;
    
    private ObservableList<Componente> datos = null;
    
    @FXML private Button modificarButton;
    @FXML private Button borrarButton;
    @FXML private Label precioTotal;
    @FXML private MenuItem cerrarMenu;
    @FXML private MenuItem modificarMenu;
    @FXML private MenuItem eliminarMenu;
    @FXML private TextField nombreTextfield;
    @FXML private BorderPane bp;
    @FXML private Button añadirButton;
    @FXML private Button guardarButton;
    @FXML private MenuItem añadirMenu;
    
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault()); 
    double doublePrecioTotal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //bp.setMinSize(1000, 600);
        configuracion = new PC();
        pcList = new ListPcWrapper();
        actualizarPcList();
        
        double doublePrecioTotal = 0;
        precioTotal.setText(currencyFormatter.format(doublePrecioTotal));
        
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaCategoria.setCellValueFactory(new PropertyValueFactory<>("cat"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaIVA.setCellValueFactory(new PropertyValueFactory<>("iva"));
        columnaTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        // Centra los elementos que contienen las columnas
        columnaCategoria.setStyle( "-fx-alignment: CENTER;");
        columnaPrecio.setStyle( "-fx-alignment: CENTER;");
        columnaCantidad.setStyle( "-fx-alignment: CENTER;");
        columnaIVA.setStyle( "-fx-alignment: CENTER;");
        columnaTotal.setStyle( "-fx-alignment: CENTER;");
        
        modificarButton.setDisable(true);
        borrarButton.setDisable(true);
        
        // oyente de foco para el listView
        tabla.focusedProperty().addListener(new ChangeListener<Boolean>(){	
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (tabla.isFocused()) {
                    borrarButton.setDisable(predeterminado);
                    modificarButton.setDisable(predeterminado);
                    eliminarMenu.setDisable(predeterminado);
                    modificarMenu.setDisable(predeterminado);
                } if(tabla.getItems().isEmpty()) {
                    borrarButton.setDisable(true);
                    modificarButton.setDisable(true);
                    eliminarMenu.setDisable(true);
                    modificarMenu.setDisable(true);
                }
            }
        });
    }    

    @FXML
    private void añadirAccion(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            estageActual.setTitle("Añadir Componente");
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/AñadirComponente.fxml"));
            BorderPane root = (BorderPane) miCargador.load();
            
            ((AñadirComponenteControlador) miCargador.getController()).setControlador(this);
            
            Scene scene = new Scene(root);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.NONE);
            estageActual.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void añadirComponente(Componente componente){
        configuracion.añadirComponente(componente);
        datos = FXCollections.observableArrayList(configuracion.getComponentes());
        tabla.setItems(datos);
      
        doublePrecioTotal += componente.getTotal();
        precioTotal.setText(currencyFormatter.format(doublePrecioTotal));
    }
    
    public void modificarComponente(Componente antiguo, Componente nuevo){
        configuracion.getComponentes().set(configuracion.getComponentes().indexOf(antiguo), nuevo);
        datos = FXCollections.observableArrayList(configuracion.getComponentes());
        tabla.setItems(datos);
        doublePrecioTotal += nuevo.getTotal();
        precioTotal.setText(currencyFormatter.format(doublePrecioTotal));
    }

    @FXML
    private void modificarAccion(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            estageActual.setTitle("Modificar Componente");
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/VistaModificar.fxml"));
            BorderPane root = (BorderPane) miCargador.load();
            
            ((VistaModificarControlador) miCargador.getController()).setControlador(this);
            ((VistaModificarControlador) miCargador.getController()).setComponente(tabla.getSelectionModel().getSelectedItem());
            
            Scene scene = new Scene(root);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.NONE);
            estageActual.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void borrarAccion(ActionEvent event) {
        int i = tabla.getSelectionModel().getSelectedIndex();
    	if (i>=0){
            doublePrecioTotal -= tabla.getSelectionModel().getSelectedItem().getTotal();
            precioTotal.setText(currencyFormatter.format(Math.abs(doublePrecioTotal)));
            configuracion.eliminarComponente(tabla.getSelectionModel().getSelectedItem());
            datos.remove(i);
        }
        if(tabla.getItems().isEmpty()) {
            borrarButton.setDisable(true);
            modificarButton.setDisable(true);
            eliminarMenu.setDisable(true);
            modificarMenu.setDisable(true);
        }
    }

    @FXML
    private void nuevaConfiguracion(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            estageActual.setTitle("Nueva Configuración");
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/VistaConfiguracion.fxml"));
            BorderPane root = (BorderPane) miCargador.load();
            
            Scene scene = new Scene(root);
            estageActual.setScene(scene);
            /* El parametro NONE es para que no bloquee los eventos en las demas ventanas
                que esten "abiertas". Esto esta puesto para poder realizar varios presupuestos
                a la vez. */
            estageActual.initModality(Modality.NONE);
            estageActual.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarConfiguracion(ActionEvent event) {
        actualizarPcList();
        try {
            Stage estageActual = new Stage();
            estageActual.setTitle("Cargar Configuración");
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/VistaCargar.fxml"));
            BorderPane root = (BorderPane) miCargador.load();
            
            ((VistaCargarControlador) miCargador.getController()).setControlador(this);
            
            Scene scene = new Scene(root);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.NONE);
            estageActual.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void configuracionesPredeterminadas(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            estageActual.setTitle("Configuraciones Predeterminadas");
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/VistaPredeterminados.fxml"));
            BorderPane root = (BorderPane) miCargador.load();
            
            ((VistaPredeterminadosControlador) miCargador.getController()).setControlador(this);
            
            Scene scene = new Scene(root);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.NONE);
            estageActual.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarConfigurador(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de alerta");
        alert.setHeaderText("¡CUIDADO!");
        alert.setContentText("Si cierras el programa los presupuestos que tengas abiertos"
                + " no se guardarán.");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
            Platform.exit();
    }
 
    @FXML
    private void añadirComponenteMenu(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            estageActual.setTitle("Añadir Componente");
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/AñadirComponente.fxml"));
            BorderPane root = (BorderPane) miCargador.load();
            
            ((AñadirComponenteControlador) miCargador.getController()).setControlador(this);
            
            Scene scene = new Scene(root);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.NONE);
            estageActual.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarComponenteMenu(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            estageActual.setTitle("Modificar Componente");
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/VistaModificar.fxml"));
            BorderPane root = (BorderPane) miCargador.load();
            
            ((VistaModificarControlador) miCargador.getController()).setControlador(this);
            ((VistaModificarControlador) miCargador.getController()).setComponente(tabla.getSelectionModel().getSelectedItem());
            
            Scene scene = new Scene(root);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.NONE);
            estageActual.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarComponenteMenu(ActionEvent event) {
        int i = tabla.getSelectionModel().getSelectedIndex();
    	if (i>=0){

            doublePrecioTotal -= tabla.getSelectionModel().getSelectedItem().getTotal();
            precioTotal.setText(currencyFormatter.format(doublePrecioTotal));
            
            configuracion.eliminarComponente(tabla.getSelectionModel().getSelectedItem());
            datos.remove(i);
        }
        if(tabla.getItems().isEmpty()) {
            double doublePrecioTotal = 0;
            precioTotal.setText(currencyFormatter.format(doublePrecioTotal));
            borrarButton.setDisable(true);
            modificarButton.setDisable(true);
        }
    }

    @FXML
    private void finalizarConfig(ActionEvent event) {
        if(configuracion.comprobarComponentes()){
            if(nombreTextfield.getText().trim().isEmpty())
                alerta("Inserte un nombre a la configuración.");
            else{
                configuracion.setNombre(nombreTextfield.getText());
                actualizarPcList();
                pcList.añadirConfiguracion(configuracion);
                try {
                    File file = new File("configuraciones.xml"); // file name
                    JAXBContext jaxbContext = JAXBContext.newInstance(ListPcWrapper.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(pcList, file); // save to a file
                    jaxbMarshaller.marshal(pcList, System.out); // echo to the console
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Diálogo de confirmación");
                alert.setHeaderText("Guardado con éxito.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        } else {
            alerta("Faltan los siguientes componentes obligatorios en la configuración:\n\n" + configuracion.componentesNecesarios());
        }
    }
    
    /**
     * Metodo que actualiza ListPcWrapper con las configuraciones que hay guardadas
     * dentro del XML.
     */
    public void actualizarPcList(){
        try {
            File file = new File("configuraciones.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ListPcWrapper.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            pcList = (ListPcWrapper) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo que muestra una alerta con un mensaje que le pasamos como
     * parametro.
     * @param mensaje string que se muestra por pantalla
     */
    public void alerta(String mensaje){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        //La siguiente linea hace que se vea todo el mensaje si este es largo (Sacada de SOF)
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public ListPcWrapper getPcList(){
        return this.pcList;
    }
    
    public void setConfiguracion(PC configuracion){
        this.configuracion = configuracion;
        doublePrecioTotal = 0;
        for(int i=0; i<configuracion.getComponentes().size(); i++)
            configuracion.getComponentes().get(i).formateoCategoria();
        for(int i=0; i<configuracion.getComponentes().size(); i++){
            doublePrecioTotal += configuracion.getComponentes().get(i).getTotal();
        }
        precioTotal.setText(currencyFormatter.format(doublePrecioTotal));
        datos = FXCollections.observableArrayList(configuracion.getComponentes());
        tabla.setItems(datos);
    }

    @FXML
    private void mensajeAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ConfiguradorPC");          
        alert.setHeaderText("    Entregable IPC");
        alert.setContentText("                      Entregable de IPC.\n                  Hecho por Kevin y Carlos.");
        alert.showAndWait();
    }

    @FXML
    private void visualizarPresupuesto(ActionEvent event) {
        try {
            Stage estageActual = new Stage();
            estageActual.setTitle("Presupuesto");
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/VistaPresupuesto.fxml"));
            BorderPane root = (BorderPane) miCargador.load();
            
            ((VistaPresupuestoControlador) miCargador.getController()).setConfiguracion(this.configuracion);
            
            Scene scene = new Scene(root);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.NONE);
            estageActual.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void desactivarBotones(){
        añadirButton.setDisable(true);
        modificarButton.setDisable(false);
        borrarButton.setDisable(true);
        guardarButton.setDisable(true);
        
        añadirMenu.setDisable(true);
        modificarMenu.setDisable(true);
        eliminarMenu.setDisable(true);
        
        predeterminado = true;
    }
}
