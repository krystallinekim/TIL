package com.beyond.object1.practice;

import java.text.DecimalFormat;

public class Product {
    private String id;
    private String name;
    private String site;
    private int price;
    private double tax;

    public Product() {
    }

    public Product(String id, String name, String site, int price, double tax) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.price = price;
        this.tax = tax;
    }

    DecimalFormat df = new DecimalFormat("0.0##");

    public void information() {

        System.out.printf("%s %s %s %d " + df.format(tax) + "\n",
                id, name, site, price);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSite() {
        return this.site;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTax() {
        return tax;
    }



    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", price=" + price +
                ", tax=" + tax +
                '}';
    }
}
