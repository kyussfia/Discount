package com.kyussfia.prototype;

public class Product {
    private final String name;
    private Double price;
    private final int count;
    private final Double unitPrice;
    private final ProductCategory category;

    public Product(String name, Double price, int count, Double unitPrice, ProductCategory category) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return this.name + "{" +
                   this.price + "£" +
                   ", " + this.count + " pcs" +
                   ", " + this.unitPrice + "£/unit" +
                   ", " + this.category +
                   '}';
    }
}
