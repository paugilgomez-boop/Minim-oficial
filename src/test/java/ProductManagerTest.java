import models.Order;
import models.Product;
import models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repositories.ProductManager;
import repositories.ProductManagerImpl;
// Starts minim1 dsa
import java.util.List;

public class ProductManagerTest {
    ProductManager pm;

    @Before
    public void setUp() {
        this.pm = ProductManagerImpl.getInstance();
        pm.addProduct("C1", "Coca-cola zero", 2);
        pm.addProduct("C2", "Coca-cola", 2.5);
        pm.addProduct("B1", "Lomo queso", 3);
        pm.addProduct("B2", "bacon queso", 3.5);
    }

    @After
    public void tearDown() {
        this.pm.clear();
    }

    @Test
    public void testProductByPrice() {
        List<Product> products = pm.getProductsByPrice();
        Assert.assertEquals(2, products.get(0).getPrice(),0);
        Assert.assertEquals(2.5, products.get(1).getPrice(), 0);
        Assert.assertEquals(3, products.get(2).getPrice(), 0);
        Assert.assertEquals(3.5, products.get(3).getPrice(), 0);
    }

    @Test
    public void testAddOrder() {
        Assert.assertEquals(0, pm.numOrders());
        Order o = new Order("381112838");
        o.addLP(2, "C1"); //, "coca-cola");
        o.addLP(1, "bocata de pernil");
        o.addLP(1, "donut");
        pm.addOrder(o);

        Assert.assertEquals(1, pm.numOrders());
    }

    @Test
    public void testDeliverOrder() {
        testAddOrder();
        Assert.assertEquals(1, pm.numOrders());
        Order o = pm.deliverOrder();
        Assert.assertEquals(0, pm.numOrders());

        Assert.assertEquals("381112838", o.getUser());
    }

    @Test
    public void testSales() {
        testDeliverOrder();

        Product p = pm.getProduct("C1");
        Assert.assertEquals(2, p.getSales());
    }

    @Test
    public void testProductsBySales() {
        testSales();

        Order o = new Order("381112838");
        o.addLP(1, "B2");
        o.addLP(3, "C2");
        pm.addOrder(o);
        pm.deliverOrder();

        List<Product> products = pm.getProductsBySales();
        Assert.assertEquals("C2", products.get(0).getId());
        Assert.assertEquals(3, products.get(0).getSales());
        Assert.assertEquals("C1", products.get(1).getId());
        Assert.assertEquals(2, products.get(1).getSales());
        Assert.assertEquals("B2", products.get(2).getId());
        Assert.assertEquals(1, products.get(2).getSales());
    }

    @Test
    public void testOrdersByUser() {
        testSales();
        User u = pm.getUser("381112838");
        List<Order> l = u.orders();
        Assert.assertEquals(1, l.size());
    }
}
