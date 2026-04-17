package models;

public class Operacion {
    private String id;
    private String expresion;
    private Double resultado;
    private String alumnoId;
    private String institutoId;

    public Operacion() {
    }

    public Operacion(String id, String expresion, String alumnoId, String institutoId) {
        this.id = id;
        this.expresion = expresion;
        this.alumnoId = alumnoId;
        this.institutoId = institutoId;
        this.resultado = null;
    }

    public Operacion(String id, String expresion, Double resultado, String alumnoId, String institutoId) {
        this.id = id;
        this.expresion = expresion;
        this.resultado = resultado;
        this.alumnoId = alumnoId;
        this.institutoId = institutoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public Double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
    }

    public String getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getInstitutoId() {
        return institutoId;
    }

    public void setInstitutoId(String institutoId) {
        this.institutoId = institutoId;
    }
}
