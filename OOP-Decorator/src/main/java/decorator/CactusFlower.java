package main.java.decorator;

public class CactusFlower extends Item {
    
    public CactusFlower() {
        this.description = "Cactus flower";
    }

    @Override
    public double price() {
        return 50.0;
    }
}