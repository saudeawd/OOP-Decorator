package main.java.decorator;

public class RomashkaFlower extends Item {
    
    public RomashkaFlower() {
        this.description = "Romashka flower";
    }

    @Override
    public double price() {
        return 30.0;
    }
}