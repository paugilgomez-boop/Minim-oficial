import models.Alumno;
import models.Instituto;
import models.Operacion;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.MathManager;
import repositories.MathManagerImpl;

import java.util.List;
import java.util.NoSuchElementException;

public class MathManagerTest {

    MathManager mm;

    @Before
    public void setUp() {
        this.mm = MathManagerImpl.getInstance();

        mm.addInstituto(new Instituto("I1", "Ins Sert"));
        mm.addInstituto(new Instituto("I2", "Ins Marines"));

        mm.addAlumno(new Alumno("A1", "Pau", "I1"));
        mm.addAlumno(new Alumno("A2", "Lucia", "I1"));
        mm.addAlumno(new Alumno("A3", "Marc", "I2"));
    }

    @After
    public void tearDown() {
        this.mm.clear();
    }

    @Test
    public void testSolicitarOperacion() {
        Assert.assertEquals(0, mm.numOperacionesPendientes());

        Operacion op = new Operacion("OP1", "5 3 +", "A1", "I1");
        mm.solicitarOperacion(op);

        Assert.assertEquals(1, mm.numOperacionesPendientes());
    }

    @Test
    public void testProcesarOperacion() {
        Operacion op = new Operacion("OP1", "5 3 +", "A1", "I1");
        mm.solicitarOperacion(op);

        Operacion resuelta = mm.procesarOperacion();

        Assert.assertEquals("OP1", resuelta.getId());
        Assert.assertEquals(8.0, resuelta.getResultado(), 0.0);
        Assert.assertEquals(0, mm.numOperacionesPendientes());
    }

    @Test
    public void testGetOperacionesByAlumno() {
        mm.solicitarOperacion(new Operacion("OP1", "5 3 +", "A1", "I1"));
        mm.solicitarOperacion(new Operacion("OP2", "10 2 /", "A1", "I1"));

        mm.procesarOperacion();
        mm.procesarOperacion();

        List<Operacion> lista = mm.getOperacionesByAlumno("A1");
        Assert.assertEquals(2, lista.size());
    }

    @Test
    public void testGetOperacionesByInstituto() {
        mm.solicitarOperacion(new Operacion("OP1", "5 3 +", "A1", "I1"));
        mm.solicitarOperacion(new Operacion("OP2", "10 2 /", "A2", "I1"));
        mm.solicitarOperacion(new Operacion("OP3", "2 2 *", "A3", "I2"));

        mm.procesarOperacion();
        mm.procesarOperacion();
        mm.procesarOperacion();

        List<Operacion> listaI1 = mm.getOperacionesByInstituto("I1");
        List<Operacion> listaI2 = mm.getOperacionesByInstituto("I2");

        Assert.assertEquals(2, listaI1.size());
        Assert.assertEquals(1, listaI2.size());
    }

    @Test
    public void testGetInstitutosByNumOperaciones() {
        mm.solicitarOperacion(new Operacion("OP1", "5 3 +", "A1", "I1"));
        mm.solicitarOperacion(new Operacion("OP2", "10 2 /", "A2", "I1"));
        mm.solicitarOperacion(new Operacion("OP3", "2 2 *", "A3", "I2"));

        mm.procesarOperacion();
        mm.procesarOperacion();
        mm.procesarOperacion();

        List<Instituto> lista = mm.getInstitutosByNumOperaciones();

        Assert.assertEquals("I1", lista.get(0).getId());
        Assert.assertEquals(2, lista.get(0).getNumOperaciones());
        Assert.assertEquals("I2", lista.get(1).getId());
        Assert.assertEquals(1, lista.get(1).getNumOperaciones());
    }

    @Test(expected = NoSuchElementException.class)
    public void testProcesarOperacionSinPendientes() {
        mm.procesarOperacion();
    }
}
