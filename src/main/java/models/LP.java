package models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LP {
    private int quantitat;
    private String identificador;

    public LP() {
    }

    public LP (int q, String s){
        quantitat = q;
        identificador = s;
    }

    public int getQuantitat(){
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public String getIdentificador(){
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
