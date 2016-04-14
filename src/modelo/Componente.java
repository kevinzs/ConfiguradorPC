
package modelo;

import es.upv.inf.Product;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Componente extends Product{
    
    private String descripcion;
    private double precio;
    private int stock;
    private Category categoria;
    private String cat;             // Categoria traducida a español
    private int cantidad;
    private double iva;
    private double total;
    
    public Componente() {
        super("",0.0,0,Category.RAM); //Componente Default
    }
    
    public Componente(String descripcion, double precio, int stock, Category categoria, int cantidad) {
        super(descripcion, precio, stock, categoria);
        this.descripcion = super.getDescription();
        this.precio = super.getPrice();
        this.stock = super.getStock();
        this.categoria = super.getCategory();
        this.cantidad = cantidad;
        this.iva = precio*0.21;
        total = cantidad*(precio+this.iva);
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat formateador = (DecimalFormat) nf;
        this.total = Double.parseDouble(formateador.format(this.total));
        this.iva = Double.parseDouble(formateador.format(this.iva));
        formateoCategoria();
    }
    
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; } 
    public String getDescripcion(){ return this.descripcion; }
    
    public void setPrecio(double precio) { this.precio = precio; }
    public double getPrecio() { return this.precio; }
    
    public int getStock() { return this.stock; }
    public void setStock(int stock) { this.stock = stock; }
    
    public void setCategoria(Category categoria) { this.categoria = categoria; }
    public Category getCategoria() { return this.categoria; }
    
    public void setCantidad(int cantidad){ this.cantidad = cantidad; }
    public int getCantidad(){ return this.cantidad; }
    
    public void setIva(double iva) { this.iva = iva; }
    public double getIva(){ return this.iva; }
    
    public void setTotal(double total) { this.total = total; }
    public double getTotal(){ return this.total; }
    
    public String getCat() { return this.cat; }
    
    /**
     * Traduce la categoria a un nombre en español para mayor
     * facilidad para el usuario.
     */
    public void formateoCategoria(){
        if(categoria == Category.CASE)
            cat = "Torre";
        else if(categoria == Category.CPU)
            cat = "Procesador";
        else if(categoria == Category.DVD_WRITER)
            cat = "Grabadora";
        else if(categoria == Category.FAN)
            cat = "Ventilador";
        else if(categoria == Category.GPU)
            cat = "Tarjeta gráfica";
        else if(categoria == Category.HDD)
            cat = "Disco Duro";
        else if(categoria == Category.HDD_SSD)
            cat = "Disco Duro SSD";
        else if(categoria == Category.KEYBOARD)
            cat = "Teclado";
        else if(categoria == Category.MOTHERBOARD)
            cat = "Placa base";
        else if(categoria == Category.MOUSE)
            cat = "Ratón";
        else if(categoria == Category.MULTIREADER)
            cat = "Multilector";
        else if(categoria == Category.POWER_SUPPLY)
            cat = "Fuente de alimentación";
        else if(categoria == Category.RAM)
            cat = "Memoria RAM";
        else if(categoria == Category.SCREEN)
            cat = "Pantalla";
        else if(categoria == Category.SPEAKER)
            cat = "Altavoz";
    }
}
