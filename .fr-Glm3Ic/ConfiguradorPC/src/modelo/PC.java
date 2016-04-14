
package modelo;

import es.upv.inf.Product.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PC {
    
    // Nombre de la configuracion del ordenador
    private String nombre;
    
    private List<Componente> componentes ;
    
    public PC() {
        componentes = new ArrayList<>();
    }
    public PC(String nombre){
        this.nombre = nombre;
        componentes = new ArrayList<>();
    }
    
    public void setNombre(String nombre){ this.nombre = nombre; }
    public String getNombre() { return this.nombre; }
    
    public void añadirComponente(Componente componente){ componentes.add(componente); }
    public void eliminarComponente(Componente componente){ componentes.remove(componente); }
    
    public void setComponentes(List<Componente> componentes){ this.componentes = componentes; }
    public List<Componente> getComponentes(){ return this.componentes; }
    
    /**
     * Comprueba si el ordenador de la configuracion tiene todos los
     * componentes obligatorios.
     * @return true si estan todos los componentes obligatorios
     */
    public boolean comprobarComponentes(){
        /* Cada elemento del array es cada uno de los componentes obligatorios.
            0 - Placa base              1 - Procesador
            2 - Memoria RAM             3 - Tarjeta Grafica
            4 - Disco Duro o SSD 
            6 - Torre                                                   */
        boolean[] aux = new boolean[6];
        for(int i = 0; i < componentes.size(); i++){
            Category cat = componentes.get(i).getCategoria();
            switch (cat) {
                case MOTHERBOARD:
                    aux[0] = true;
                    break;
                case CPU:
                    aux[1] = true;
                    break;
                case RAM:
                    aux[2] = true;
                    break;
                case GPU:
                    aux[3] = true;
                    break;
                case HDD:
                    aux[4] = true;
                    break;
                case HDD_SSD:
                    aux[4] = true;
                    break;
                case CASE:
                    aux[5] = true;
                    break;
                default:
                    break;
            }
        }
        for(int i = 0; i < aux.length; i++)
            if(aux[i] == false) return false;
        return true;
    }
    
    public String componentesNecesarios(){
        Map<String, Boolean> aux = new HashMap<>(6);
        aux.put("\t- Placa Base\n",false);
        aux.put("\t- Procesador\n",false);
        aux.put("\t- Memoria RAM\n",false);
        aux.put("\t- Tarjeta Grafica\n",false);
        aux.put("\t- Disco Duro o SSD\n",false);
        aux.put("\t- Torre\n",false);
        for (int i = 0; i < componentes.size(); i++){
            Category cat = componentes.get(i).getCategoria();
            switch (cat) {
                case MOTHERBOARD:
                    aux.put("\t- Placa Base\n",true);
                    break;
                case CPU:
                    aux.put("\t- Procesador\n",true);
                    break;
                case RAM:
                    aux.put("\t- Memoria RAM\n",true);
                    break;
                case GPU:
                    aux.put("\t- Tarjeta Grafica\n",true);
                    break;
                case HDD:
                    aux.put("\t- Disco Duro o SSD\n",true);
                    break;
                case HDD_SSD:
                    aux.put("\t- Disco Duro o SSD\n",true);
                    break;
                case CASE:
                    aux.put("\t- Torre\n",true);
                    break;
                default:
                    break;
            }
        }
        String str = "";
        for (String c : aux.keySet()){
            if (aux.get(c) == false) {
                str += c;
            }
        }
        return str;
    
    }
}
