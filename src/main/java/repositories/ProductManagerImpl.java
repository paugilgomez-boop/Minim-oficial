package repositories;

import models.LP;
import models.Order;
import models.Product;
import models.User;
import org.apache.log4j.Logger;

import java.util.*;

public class ProductManagerImpl implements ProductManager {
    private static ProductManagerImpl instance;
    final static Logger logger = Logger.getLogger(ProductManagerImpl.class);
    private List<Product> productList;
    private Queue<Order> orderQueue;
    private HashMap<String, User> users;

    private ProductManagerImpl() {
        productList = new ArrayList<>();
        orderQueue = new LinkedList<>();
        users = new HashMap<>();
    }

    public static ProductManagerImpl getInstance() {
        logger.info("getInstance()");
        if (instance == null) {
            instance = new ProductManagerImpl();
            logger.info("new repositories.ProductManagerImpl()");
        }
        logger.info("getInstance completed");
        return instance;
    }

    @Override
    public void clear() {
        logger.info("clear()");
        productList.clear();
        orderQueue.clear();
        users.clear();
        logger.info("clear completed");
    }


    @Override
    public void addProduct(String id, String name, double price) {
        logger.info("addProduct(" + id + ", " + name + ", " + price + ")");
        productList.add(new Product(id, name, price));
        logger.info("addProduct completed");
    }

    @Override
    public List<Product> getProductsByPrice() {
        logger.info("getProductsByPrice()");
        List<Product> copyProductList = new ArrayList<>(productList);
        copyProductList.sort((p1,p2)-> Double.compare(p1.getPrice(), p2.getPrice()));
        logger.info("getProductsByPrice completed: " + copyProductList.size());
        return copyProductList;
    }

    @Override
    public List<Product> getProductsBySales() {
        logger.info("getProductsBySales()");
        List<Product> copyProductList = new ArrayList<>(productList);
        copyProductList.sort((p1,p2)-> Double.compare(p2.getSales(), p1.getSales()));
        logger.info("getProductsBySales completed: " + copyProductList.size());
        return copyProductList;
    }

    @Override
    public void addOrder(Order order) {
        logger.info("addOrder(" + order + ")");
        if (order == null) {
            logger.error("null order");
            return;
        }
        orderQueue.add(order);
        logger.info("addOrder completed: " + orderQueue.size());
    }

    @Override
    public int numOrders() {
        logger.info("numOrders()");
        int size = orderQueue.size();
        logger.info("numOrders completed: " + size);
        return size;
    }

    @Override
    public Order deliverOrder() {
        logger.info("deliverOrder()");
        Order order = orderQueue.poll();
        if (order == null) {
            logger.error("no pending orders");
            return null;
        }

        User u = users.get(order.getUser());
        if (u == null) {
            u = new User(order.getUser());
            users.put(order.getUser(), u);
        }
        u.addOrder(order);

        for (LP lp : order.getComanda()) {
            Product p;
            p = getProduct(lp.getIdentificador());
            if(p != null){
                p.addSales(lp.getQuantitat());
            } else {
                logger.error("product not found: " + lp.getIdentificador());
            }
        }
        logger.info("deliverOrder completed: " + order.getUser());
        return order;
    }

    @Override
    public Product getProduct(String c1) {
        logger.info("getProduct(" + c1 + ")");
        for(Product p: productList){
            if(p.getId().equals(c1) || p.getName().equals(c1)){
                logger.info("getProduct completed: " + p);
                return p;
            }
        }
        logger.error("product not found: " + c1);
        return null;
    }

    @Override
    public User getUser(String number) {
        logger.info("getUser(" + number + ")");
        User user = users.get(number);
        if (user == null) {
            logger.error("user not found: " + number);
        } else {
            logger.info("getUser completed: " + number);
        }
        return user;
    }

    @Override
    public List<Order> OrdersByUser(String number) {
        logger.info("OrdersByUser(" + number + ")");
        User u = getUser(number);
        if (u == null) {
            logger.error("OrdersByUser user not found: " + number);
            return new ArrayList<>();
        }
        List<Order> ordersU = u.orders();
        logger.info("OrdersByUser completed: " + ordersU.size());
        return ordersU;
    }
}
