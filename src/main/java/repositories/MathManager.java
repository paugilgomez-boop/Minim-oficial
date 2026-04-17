package repositories;

import models.Alumno;
import models.Instituto;
import models.Operacion;

import java.util.List;

public interface MathManager {

    void addAlumno(Alumno alumno);

    void addInstituto(Instituto instituto);

    void solicitarOperacion(Operacion operacion);

    Operacion procesarOperacion();

    List<Operacion> getOperacionesByInstituto(String institutoId);

    List<Operacion> getOperacionesByAlumno(String alumnoId);

    List<Instituto> getInstitutosByNumOperaciones();

    Alumno getAlumno(String alumnoId);

    Instituto getInstituto(String institutoId);

    int numOperacionesPendientes();

    void clear();
}
