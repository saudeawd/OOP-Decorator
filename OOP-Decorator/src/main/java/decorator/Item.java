package main.java.decorator;

public abstract class Item {
    protected String description;
    
    public abstract double price();
    
    public String getDescription() {
        return description;
    }
}