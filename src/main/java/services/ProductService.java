package services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import models.Order;
import models.Product;
import repositories.ProductManager;
import repositories.ProductManagerImpl;
import requests.OrderRequest;
import requests.ProductRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/manager", description = "Endpoint to Product Manager Service")
@Path("/manager")
public class ProductService {

    private final ProductManager pm;

    public ProductService() {
        this.pm = ProductManagerImpl.getInstance();


    }

    @GET
    @Path("/products/price")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get products sorted by price")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Product.class, responseContainer = "List")
    })
    public Response getProductsByPrice() {
        List<Product> products = this.pm.getProductsByPrice();
        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @Path("/products/sales")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get products sorted by sales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Product.class, responseContainer = "List")
    })
    public Response getProductsBySales() {
        List<Product> products = this.pm.getProductsBySales();
        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(200).entity(entity).build();
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public Response addProduct(ProductRequest request) {
        if (request == null || request.getId() == null || request.getName() == null) {
            return Response.status(400).entity(request).build();
        }

        this.pm.addProduct(request.getId(), request.getName(), request.getPrice());
        Product product = this.pm.getProduct(request.getId());
        return Response.status(201).entity(product).build();
    }

    @POST
    @Path("/orders")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Order.class),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public Response addOrder(OrderRequest request) {
        if (request == null || request.getUser() == null) {
            return Response.status(400).entity(request).build();
        }

        Order order = new Order(request.getUser());
        if (request.getComanda() == null || request.getComanda().isEmpty()) {
            return Response.status(400).entity(request).build();
        }

        if (request.getComanda() != null) {
            for (OrderRequest.LPRequest lp : request.getComanda()) {
                if (lp.getIdentificador() == null) {
                    return Response.status(400).entity(request).build();
                }
                order.addLP(lp.getQuantitat(), lp.getIdentificador());
            }
        }

        this.pm.addOrder(order);
        return Response.status(201).entity(order).build();
    }

    @POST
    @Path("/orders/deliver")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deliver next order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Order.class),
            @ApiResponse(code = 404, message = "No pending orders")
    })
    public Response deliverOrder() {
        Order order = this.pm.deliverOrder();
        if (order == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(order).build();
    }

    @GET
    @Path("/users/{dni}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get orders by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Order.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    public Response getOrdersByUser(@PathParam("dni") String dni) {
        if (this.pm.getUser(dni) == null) {
            return Response.status(404).build();
        }

        List<Order> orders = this.pm.OrdersByUser(dni);
        GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(orders) {};
        return Response.status(200).entity(entity).build();
    }
}
