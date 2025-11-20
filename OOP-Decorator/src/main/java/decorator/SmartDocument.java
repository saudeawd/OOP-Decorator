package main.java.decorator;

public class SmartDocument implements Document {
    private String gcsPath;

    public SmartDocument(String gcsPath) {
        this.gcsPath = gcsPath;
    }

    @Override
    public String parse() {
        // Спрощена версія - Google Cloud Vision вимагає конфігурації
        System.out.println("Parsing document from: " + gcsPath);
        // Імітація складного парсингу
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Parsed content from " + gcsPath;
    }
}