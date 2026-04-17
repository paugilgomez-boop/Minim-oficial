package models;

public class Alumno {
    private String id;
    private String nombre;
    private String institutoId;

    public Alumno() {
    }

    public Alumno(String id, String nombre, String institutoId) {
        this.id = id;
        this.nombre = nombre;
        this.institutoId = institutoId;
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

    public String getInstitutoId() {
        return institutoId;
    }

    public void setInstitutoId(String institutoId) {
        this.institutoId = institutoId;
    }
}
