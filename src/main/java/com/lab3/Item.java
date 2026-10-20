package com.lab3;

import java.util.Date;

public class Item extends AbstractItem {
    private double price;
    private int ramSize;
    private Date releaseDate;

    public Item(int id, String modelName, double price, int ramSize, Date releaseDate) {
        super(id, modelName);
        this.price = price;
        this.ramSize = ramSize;
        this.releaseDate = releaseDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    @Override 
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getRamSize() { return ramSize; }
    public void setRamSize(int ramSize) { this.ramSize = ramSize; }

    public Date getReleaseDate() { return releaseDate; }
    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }

    @Override
    public String toString() {
        return "Device [ID=" + id + ", Model=" + modelName + ", Price=" + price + 
               ", RAM=" + ramSize + "GB, Date=" + releaseDate + "]";
    }
}