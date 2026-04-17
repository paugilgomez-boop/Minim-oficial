import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.ReversePolishNotation;
import repositories.ReversePolishNotationImpl;

public class ReversePolishNotationTest {

    ReversePolishNotation rpn;

    @Before
    public void setUp() {
        this.rpn = new ReversePolishNotationImpl();
    }

    @Test
    public void testSuma() {
        double resultado = rpn.calcular("5 3 +");
        Assert.assertEquals(8.0, resultado, 0.0);
    }

    @Test
    public void testResta() {
        double resultado = rpn.calcular("8 3 -");
        Assert.assertEquals(5.0, resultado, 0.0);
    }

    @Test
    public void testMultiplicacion() {
        double resultado = rpn.calcular("4 2 *");
        Assert.assertEquals(8.0, resultado, 0.0);
    }

    @Test
    public void testDivision() {
        double resultado = rpn.calcular("10 2 /");
        Assert.assertEquals(5.0, resultado, 0.0);
    }

    @Test
    public void testOperacionCompleja() {
        double resultado = rpn.calcular("5 1 2 + 4 * + 3 -");
        Assert.assertEquals(14.0, resultado, 0.0);
    }
}
