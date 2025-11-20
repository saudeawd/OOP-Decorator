package main.java.decorator;

public abstract class ItemDecorator extends Item {
    protected Item item;

    public ItemDecorator(Item item) {
        this.item = item;
    }

    public abstract double price();
    
    @Override
    public String getDescription() {
        return item.getDescription();
    }
}