package com.lab3;

public abstract class AbstractItem {
    protected int id;
    protected String modelName;

    public AbstractItem(int id, String modelName) {
        this.id = id;
        this.modelName = modelName;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return modelName;
    }

    public abstract double getPrice();
}
