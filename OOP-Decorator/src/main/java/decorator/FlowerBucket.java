package main.java.decorator;

import java.util.ArrayList;
import java.util.List;

public class FlowerBucket extends Item {
    private List<Item> items = new ArrayList<>();

    public FlowerBucket() {
        this.description = "Flower Bucket";
    }

    public void addFlower(Item item) {
        items.add(item);
    }

    @Override
    public double price() {
        return items.stream()
                .mapToDouble(Item::price)
                .sum();
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder(description + " contains:\n");
        for (Item item : items) {
            sb.append("  - ").append(item.getDescription())
              .append(" ($").append(item.price()).append(")\n");
        }
        return sb.toString();
    }
}