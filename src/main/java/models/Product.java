package models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product{

    private  String id;
    private  String name;
    private double price;
    private int sales;

    public Product() {
    }

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sales = 0;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSales() {
        return this.sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public void addSales(int quantity){
        this.sales += quantity;
    }
}

