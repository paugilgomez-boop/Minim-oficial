package models;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
    private String dni;
    private List<LP> comanda;

    public Order() {
        this.comanda = new ArrayList<>();
    }

    public Order(String dni) {
        this.dni = dni;
        this.comanda = new ArrayList<>();
    }

    public void addLP(int i, String s) {
        this.comanda.add(new LP(i,s));
    }

    public String getUser() {
        return this.dni;
    }

    public void setUser(String dni) {
        this.dni = dni;
    }

    public List<LP> getComanda() {
        return this.comanda;
    }

    public void setComanda(List<LP> comanda) {
        this.comanda = comanda;
    }
}
