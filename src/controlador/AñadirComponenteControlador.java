
package controlador;

import es.upv.inf.Database;
import es.upv.inf.Product;
import es.upv.inf.Product.Category;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Componente;

public class AñadirComponenteControlador implements Initializable {

    @FXML private ChoiceBox<String> choiceBox;
    ObservableList componentesList = FXCollections.observableArrayList(
            "Placa base","Procesador","Memoria RAM","Tarjeta gráfica",
            "Disco duro","Disco duro SSD","Torre",new Separator(),
            "Teclado","Ratón","Monitor","Altavoces","Multilectores",
            "Grabadora","Ventilador","Fuente de alimentación"
    );
    
    @FXML private TableView<Product> tabla;
    @FXML private TableColumn<Product, String> columnaNombre;
    @FXML private TableColumn<Product, Double> columnaPrecio;
    @FXML private TableColumn<Product, Integer> columnaDisponibilidad;
    
    private ObservableList<Product> datos = null;
    
    private List<Product> componentesTabla = new ArrayList<>();
    
    private VistaConfiguracionControlador controlador;
    
    private Category categoria;
    
    @FXML private TextField cantidadTextfield;
    @FXML private TextField nombreTextField;
    @FXML private TextField precioMin;
    @FXML private TextField precioMax;
    @FXML private CheckBox stockCheckBox;
    @FXML private Button buscarButton;
    @FXML private Button añadirButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeAux();
    }    
    
    //Para poder llamar a initialize en busquedaAdicional()
    public void initializeAux(){
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnaDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        // Centra los elementos que contienen las columnas
        columnaPrecio.setStyle( "-fx-alignment: CENTER;");
        columnaDisponibilidad.setStyle( "-fx-alignment: CENTER;");
        
        categoria = Category.MOTHERBOARD;
        
        choiceBox.setValue("Placa base");
        choiceBox.setItems(componentesList);
        
        // Actualiza la tabla si seleccionamos una opcion diferende de la choiceBox
        choiceBox.valueProperty().addListener(new ChangeListener(){
           @Override
           public void changed(ObservableValue o, Object oldVal, Object newVal){
               int position = choiceBox.getSelectionModel().getSelectedIndex();
               switch(position){
                    case 0:
                       categoria = Category.MOTHERBOARD;
                       componentesTabla = Database.getProductByCategory(Product.Category.MOTHERBOARD);
                       break;
                    case 1:
                        categoria = Category.CPU;
                        componentesTabla = Database.getProductByCategory(Product.Category.CPU);
                        break;
                    case 2:
                        categoria = Category.RAM;
                        componentesTabla = Database.getProductByCategory(Product.Category.RAM);
                       break;
                    case 3:
                        categoria = Category.GPU;
                        componentesTabla = Database.getProductByCategory(Product.Category.GPU);
                        break;
                    case 4:
                        categoria = Category.HDD;
                        componentesTabla = Database.getProductByCategory(Product.Category.HDD);
                       break;
                    case 5:
                        categoria = Category.HDD_SSD;
                        componentesTabla = Database.getProductByCategory(Product.Category.HDD_SSD);
                        break;
                    case 6:
                        categoria = Category.CASE;
                        componentesTabla = Database.getProductByCategory(Product.Category.CASE);
                       break;
                    case 8:
                        categoria = Category.KEYBOARD;
                        componentesTabla = Database.getProductByCategory(Product.Category.KEYBOARD);
                        break;
                    case 9:
                        categoria = Category.MOUSE;
                        componentesTabla = Database.getProductByCategory(Product.Category.MOUSE);
                       break;
                    case 10:
                        categoria = Category.SCREEN;
                        componentesTabla = Database.getProductByCategory(Product.Category.SCREEN);
                        break;
                    case 11:
                        categoria = Category.SPEAKER;
                        componentesTabla = Database.getProductByCategory(Product.Category.SPEAKER);
                       break;
                    case 12:
                        categoria = Category.MULTIREADER;
                        componentesTabla = Database.getProductByCategory(Product.Category.MULTIREADER);
                        break;
                    case 13:
                        categoria = Category.DVD_WRITER;
                        componentesTabla = Database.getProductByCategory(Product.Category.DVD_WRITER);
                       break;
                    case 14:
                        categoria = Category.FAN;
                        componentesTabla = Database.getProductByCategory(Product.Category.FAN);
                        break;
                    case 15:
                        categoria = Category.POWER_SUPPLY;
                        componentesTabla = Database.getProductByCategory(Product.Category.POWER_SUPPLY);
                       break;
                    default:
                       break;
                    
               }
               datos = FXCollections.observableArrayList(componentesTabla);
               tabla.setItems(datos);
           }
        });
        componentesTabla = Database.getProductByCategory(Product.Category.MOTHERBOARD);
        datos = FXCollections.observableArrayList(componentesTabla);
        tabla.setItems(datos);
    }

    @FXML
    private void añadirComponente(ActionEvent event) {
        Product seleccion = tabla.getSelectionModel().getSelectedItem();
        int cantidad = 1;
        boolean flag = true;
        if (isInteger(cantidadTextfield.getText())){
            cantidad = Integer.parseInt(cantidadTextfield.getText());
            if(cantidad > seleccion.getStock()) {
                alerta("No hay tantas unidades disponibles del componente seleccionado.");
                flag = false;
            }
        } else if (!cantidadTextfield.getText().isEmpty()){
            alerta("Introduzca una cantidad válida.");
            flag = false;
        }
        if (seleccion != null && flag){
            controlador.añadirComponente(new Componente(seleccion.getDescription(),
                    seleccion.getPrice(),seleccion.getStock(),seleccion.getCategory(),
                    cantidad));
            //Cerramos la ventana
            Node nodo = (Node) event.getSource();
            nodo.getScene().getWindow().hide();
        } else if (seleccion == null) {
            alerta("Selecciona un componente de la tabla.");
        }
    }
    
    @FXML
    private void busquedaAdicional(ActionEvent event) {
        if(!nombreTextField.getText().trim().isEmpty()
                && (!precioMin.getText().trim().isEmpty()
                || !precioMax.getText().trim().isEmpty())){
            // comprobamos que los precios estan bien
            if(isInteger(precioMin.getText()) && isInteger(precioMax.getText())) {
                /* Busca los componentes los cuales coinciden con el nombre y el
                    rango de precio indicados en las opciones adicionales y los
                    muestra en la tabla */
                componentesTabla = Database.getProductByCategoryDescriptionAndPrice(categoria,
                        nombreTextField.getText().trim(),
                        Double.parseDouble(precioMin.getText()), 
                        Double.parseDouble(precioMax.getText()), stockCheckBox.isSelected());
            } else if (isInteger(precioMin.getText()) && precioMax.getText().isEmpty()) {
                componentesTabla = Database.getProductByCategoryDescriptionAndPrice(categoria,
                        nombreTextField.getText().trim(),
                        Double.parseDouble(precioMin.getText()), 
                        99999999, stockCheckBox.isSelected());
            } else if (precioMin.getText().isEmpty() && isInteger(precioMax.getText())) {
                componentesTabla = Database.getProductByCategoryDescriptionAndPrice(categoria,
                        nombreTextField.getText().trim(),0, 
                        Double.parseDouble(precioMax.getText()), stockCheckBox.isSelected());
            } else 
                alerta("Introduzca precios válidos.");
            datos = FXCollections.observableArrayList(componentesTabla);
            tabla.setItems(datos);
        } else if(!precioMin.getText().trim().isEmpty() || !precioMax.getText().trim().isEmpty()) {           
            if(isInteger(precioMin.getText()) && isInteger(precioMax.getText())) {
                /* Busca los componentes que estan dentro del rango especificado en las
                    opciones adicionales de busqueda y los muestra en la tabla */
                componentesTabla = Database.getProductByCategoryAndPrice(categoria,
                        Double.parseDouble(precioMin.getText()), 
                        Double.parseDouble(precioMax.getText()), stockCheckBox.isSelected());
            } else if (isInteger(precioMin.getText()) && precioMax.getText().isEmpty()) {
                componentesTabla = Database.getProductByCategoryAndPrice(categoria,
                        Double.parseDouble(precioMin.getText()), 
                        99999999, stockCheckBox.isSelected());
            } else if (precioMin.getText().isEmpty() && isInteger(precioMax.getText())) {
                componentesTabla = Database.getProductByCategoryAndPrice(categoria,0, 
                        Double.parseDouble(precioMax.getText()), stockCheckBox.isSelected());
            } else 
                alerta("Introduzca precios válidos.");
            datos = FXCollections.observableArrayList(componentesTabla);
            tabla.setItems(datos);
        }
        else if(!nombreTextField.getText().trim().isEmpty()){
            /* Busca los componentes que coinciden con el nombre especificado en las
                    opciones adicionales de busqueda y los muestra en la tabla */
            componentesTabla = Database.getProductByCategoryAndDescription(categoria,
                    nombreTextField.getText().trim(), stockCheckBox.isSelected());
            datos = FXCollections.observableArrayList(componentesTabla);
            tabla.setItems(datos);
        } else {
            initializeAux();
        }
    }
    
    @FXML
    private void cancelarAccion(ActionEvent event) {
        //Cerramos la ventana
        Node nodo = (Node) event.getSource();
        nodo.getScene().getWindow().hide();
    }
    
    /**
     * Metodo que muestra una alerta con un mensaje que le pasamos como
     * parametro.
     * @param mensaje string que se muestra por pantalla
     */
    public void alerta(String mensaje){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void setControlador(VistaConfiguracionControlador controlador){
        this.controlador = controlador;
    }
    
    /**
     * Metodo para comprobar si un String es un numero o no
     * @param s String a comprobar
     * @return true si es numero, false si es otra cosa
     */
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException | NullPointerException e) { 
            return false; 
        }
        return true;
    }
}
