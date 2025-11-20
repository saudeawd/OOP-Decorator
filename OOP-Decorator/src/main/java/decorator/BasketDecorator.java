package main.java.decorator;

public class BasketDecorator extends ItemDecorator {
    
    public BasketDecorator(Item item) {
        super(item);
    }

    @Override
    public double price() {
        return 4 + item.price();
    }

    @Override
    public String getDescription() {
        return item.getDescription() + " + Basket wrapping";
    }
}