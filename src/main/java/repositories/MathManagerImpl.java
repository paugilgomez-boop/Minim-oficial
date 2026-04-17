package repositories;

import models.Alumno;
import models.Instituto;
import models.Operacion;

import org.apache.log4j.Logger;

import java.util.*;

public class MathManagerImpl implements MathManager {

    private static MathManagerImpl instance;
    final static Logger logger = Logger.getLogger(MathManagerImpl.class);

    private ReversePolishNotation rpn;
    private HashMap<String, Alumno> alumnos;
    private HashMap<String, Instituto> institutos;
    private Queue<Operacion> operacionesPendientes;
    private HashMap<String, List<Operacion>> operacionesPorAlumno;
    private HashMap<String, List<Operacion>> operacionesPorInstituto;

    private MathManagerImpl() {
        rpn = new ReversePolishNotationImpl();
        alumnos = new HashMap<>();
        institutos = new HashMap<>();
        operacionesPendientes = new LinkedList<>();
        operacionesPorAlumno = new HashMap<>();
        operacionesPorInstituto = new HashMap<>();
    }

    public static MathManagerImpl getInstance() {
        logger.info("getInstance()");
        if (instance == null) {
            instance = new MathManagerImpl();
            logger.info("new repositories.MathManagerImpl()");
        }
        logger.info("getInstance completed" + instance);
        return instance;
    }

    @Override
    public void clear() {
        logger.info("clear()");

        alumnos.clear();
        institutos.clear();
        operacionesPendientes.clear();
        operacionesPorAlumno.clear();
        operacionesPorInstituto.clear();

        logger.info("clear completed");
    }

    @Override
    public void addAlumno(Alumno alumno) {
        logger.info("addAlumno(" + alumno.getId() + ", " + alumno.getNombre() + ", " + alumno.getInstitutoId() + ")");

        alumnos.put(alumno.getId(), alumno);

        logger.info("addAlumno completed: " + alumno.getId());
    }

    @Override
    public void addInstituto(Instituto instituto) {
        logger.info("addInstituto(" + instituto.getId() + ", " + instituto.getNombre() + ")");

        institutos.put(instituto.getId(), instituto);

        logger.info("addInstituto completed: " + instituto.getId());
    }

    @Override
    public void solicitarOperacion(Operacion operacion) {
        logger.info("solicitarOperacion(" + operacion.getId() + ", " + operacion.getExpresion() + ", " + operacion.getAlumnoId() + ", " + operacion.getInstitutoId() + ")");

        if (!alumnos.containsKey(operacion.getAlumnoId())) {
            logger.error("solicitarOperacion falla: alumno no existe " + operacion.getAlumnoId());
            throw new NoSuchElementException("No existeix cap alumne amb aquest id");
        }

        Alumno alumno = alumnos.get(operacion.getAlumnoId());
        if (!alumno.getInstitutoId().equals(operacion.getInstitutoId())) {
            logger.error("solicitarOperacion falla: el alumno " + operacion.getAlumnoId() + " no pertenece al instituto " + operacion.getInstitutoId());
            throw new IllegalArgumentException("L'alumne no pertany a aquest institut");
        }

        if (!institutos.containsKey(operacion.getInstitutoId())) {
            logger.error("solicitarOperacion falla: instituto no existe " + operacion.getInstitutoId());
            throw new NoSuchElementException("No existeix cap institut amb aquest id");
        }

        operacionesPendientes.add(operacion);

        logger.info("solicitarOperacion completed: pending=" + operacionesPendientes.size());
    }

    @Override
    public Operacion procesarOperacion() {
        logger.info("procesarOperacion()");

        if (operacionesPendientes.isEmpty()) {
            logger.error("procesarOperacion falla: no hay operaciones pendientes");
            throw new NoSuchElementException("No hi ha operacions pendents");
        }

        Operacion operacion = operacionesPendientes.poll();
        double resultado = rpn.calcular(operacion.getExpresion());
        operacion.setResultado(resultado);

        if (!operacionesPorAlumno.containsKey(operacion.getAlumnoId())) {
            operacionesPorAlumno.put(operacion.getAlumnoId(), new ArrayList<Operacion>());
        }
        operacionesPorAlumno.get(operacion.getAlumnoId()).add(operacion);

        if (!operacionesPorInstituto.containsKey(operacion.getInstitutoId())) {
            operacionesPorInstituto.put(operacion.getInstitutoId(), new ArrayList<Operacion>());
        }
        operacionesPorInstituto.get(operacion.getInstitutoId()).add(operacion);

        Instituto instituto = institutos.get(operacion.getInstitutoId());
        instituto.setNumOperaciones(instituto.getNumOperaciones() + 1);

        logger.info("procesarOperacion completado: " + operacion.getId() + ", resultado=" + operacion.getResultado());
        return operacion;
    }

    @Override
    public List<Operacion> getOperacionesByInstituto(String institutoId) {
        logger.info("getOperacionesByInstituto(" + institutoId + ")");

        List<Operacion> lista = operacionesPorInstituto.getOrDefault(institutoId, new ArrayList<Operacion>());

        logger.info("getOperacionesByInstituto completado: " + lista.size());
        return lista;
    }

    @Override
    public List<Operacion> getOperacionesByAlumno(String alumnoId) {
        logger.info("getOperacionesByAlumno(" + alumnoId + ")");

        List<Operacion> lista = operacionesPorAlumno.getOrDefault(alumnoId, new ArrayList<Operacion>());

        logger.info("getOperacionesByAlumno completado: " + lista.size());
        return lista;
    }
    @Override
    public List<Instituto> getInstitutosByNumOperaciones() {
        logger.info("getInstitutosByNumOperaciones()");

        List<Instituto> lista = new ArrayList<>(institutos.values());
        lista.sort((i1, i2) -> Integer.compare(i2.getNumOperaciones(), i1.getNumOperaciones()));

        logger.info("getInstitutosByNumOperaciones completado: " + lista.size());
        return lista;
    }
    @Override
    public Alumno getAlumno(String alumnoId) {
        logger.info("getAlumno(" + alumnoId + ")");
        Alumno alumno = alumnos.get(alumnoId);
        logger.info("getAlumno completado: " + (alumno == null ? "null" : alumno.getId()));
        return alumno;
    }
    @Override
    public Instituto getInstituto(String institutoId) {
        logger.info("getInstituto(" + institutoId + ")");
        Instituto instituto = institutos.get(institutoId);
        logger.info("getInstituto completado: " + (instituto == null ? "null" : instituto.getId()));
        return instituto;
    }
    @Override
    public int numOperacionesPendientes() {
        logger.info("numOperacionesPendientes()");
        int num = operacionesPendientes.size();
        logger.info("numOperacionesPendientes completado: " + num);
        return num;
    }
}
