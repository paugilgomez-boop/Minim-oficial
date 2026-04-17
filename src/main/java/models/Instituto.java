package models;

public class Instituto {
    private String id;
    private String nombre;
    private int numOperaciones;

    public Instituto() {
    }

    public Instituto(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.numOperaciones = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumOperaciones() {
        return numOperaciones;
    }

    public void setNumOperaciones(int numOperaciones) {
        this.numOperaciones = numOperaciones;
    }

    public void sumarOperacion() {
        this.numOperaciones++;
    }
}
