package main.java.decorator;

public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("DEMONSTRATION OF DECORATORS FOR FLOWER BOUQUETS");
        System.out.println("=".repeat(60));

        FlowerBucket bucket = new FlowerBucket();
        bucket.addFlower(new CactusFlower());
        bucket.addFlower(new RomashkaFlower());

        System.out.println("\n1. Simple bouquet:");
        System.out.println(bucket.getDescription());
        System.out.println("Total cost: $" + bucket.price());

        Item decoratedBucket = new PaperDecorator(bucket);
        System.out.println("\n2. Bouquet with paper wrapping:");
        System.out.println(decoratedBucket.getDescription());
        System.out.println("Total cost: $" + decoratedBucket.price());

        decoratedBucket = new BasketDecorator(decoratedBucket);
        System.out.println("\n3. Bouquet with basket wrapping:");
        System.out.println(decoratedBucket.getDescription());
        System.out.println("Total cost: $" + decoratedBucket.price());

        decoratedBucket = new RibbonDecorator(decoratedBucket);
        System.out.println("\n4. Fully decorated bouquet:");
        System.out.println(decoratedBucket.getDescription());
        System.out.println("Total cost: $" + decoratedBucket.price());

        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATION OF DOCUMENT PROCESSING WITH DECORATORS");
        System.out.println("=".repeat(60));

        Document simpleDoc = new SmartDocument("gs://test-bucket/document.jpg");
        Document timedDoc = new TimedDocument(simpleDoc);
        Document cachedDoc = new CachedDocument(timedDoc, "test-document-1");
        
        System.out.println("\nFirst call (will parse and cache):");
        String result1 = cachedDoc.parse();
        System.out.println("Result: " + result1.substring(0, Math.min(50, result1.length())) + "...");
        
        System.out.println("\nSecond call (should use cache):");
        String result2 = cachedDoc.parse();
        System.out.println("Result: " + result2.substring(0, Math.min(50, result2.length())) + "...");

        CachedDocument.clearCache();
    }
}