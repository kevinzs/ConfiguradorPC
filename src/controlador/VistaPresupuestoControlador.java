
package controlador;

import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.Componente;
import modelo.PC;


public class VistaPresupuestoControlador implements Initializable {
    
    private PC configuracion;
    private ObservableList<Componente> datos = null;

    private TableView<Componente> tabla;
    @FXML private TableColumn<Componente, String> columnaNombre;
    @FXML private TableColumn<Componente, String> columnaCategoria;
    @FXML private TableColumn<Componente, Double> columnaPrecio;
    @FXML private TableColumn<Componente, Integer> columnaCantidad;
    @FXML private TableColumn<Componente, Integer> columnaIVA;
    @FXML private TableColumn<Componente, Double> columnaTotal;
    
    @FXML private Font x1;
    
    @FXML private Label labelTotalSinIVA;
    @FXML private Label labelTotalIVA;
    @FXML private Label labelPrecioTotal;
    @FXML private Label labelFecha;
    
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault()); 
    double doublePrecioTotal;
    double doubleTotalSinIVA;
    double doubleTotalIVA;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configuracion = new PC();
        
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaCategoria.setCellValueFactory(new PropertyValueFactory<>("cat"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaIVA.setCellValueFactory(new PropertyValueFactory<>("iva"));
        columnaTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        labelFecha.setText(dateFormat.format(new Date()));
        
        columnaPrecio.setStyle( "-fx-alignment: CENTER;");
    }
    

    
    public void setConfiguracion(PC configuracion){
        this.configuracion = configuracion;
        doublePrecioTotal = 0;
        for(int i=0; i<configuracion.getComponentes().size(); i++)
            configuracion.getComponentes().get(i).formateoCategoria();
        for(int i=0; i<configuracion.getComponentes().size(); i++){
            doublePrecioTotal += configuracion.getComponentes().get(i).getTotal();
            doubleTotalIVA += configuracion.getComponentes().get(i).getIva();
        }
        doubleTotalSinIVA += doublePrecioTotal - doubleTotalIVA;
        labelPrecioTotal.setText(currencyFormatter.format(doublePrecioTotal));
        labelTotalSinIVA.setText(currencyFormatter.format(doubleTotalSinIVA));
        labelTotalIVA.setText(currencyFormatter.format(doubleTotalIVA));
        datos = FXCollections.observableArrayList(configuracion.getComponentes());
        //tabla.setItems(datos); BUG: SALTA NULL POINTER 
    }

    @FXML
    private void imprimirButtonClicked(ActionEvent event) {
        /*Stage estageActual = new Stage();
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(estageActual.getScene().getWindow()));{
            boolean success = job.printPage(estageActual);
            if (success)
                job.endJob();
        }*/
        
    }
}
