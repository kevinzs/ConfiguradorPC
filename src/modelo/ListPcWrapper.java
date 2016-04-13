
package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListPcWrapper {
    
    private List<PC> pcList;
    
    public ListPcWrapper() {
        pcList = new ArrayList<>();
    }
    
    @XmlElement(name = "PC")
    public List<PC> getPCList() {
        return pcList;
    }
    public void setPCList(List<PC> list) {
        pcList = list;
    }
    
    public void añadirConfiguracion(PC configuracion){
        pcList.add(configuracion);
    }
}
