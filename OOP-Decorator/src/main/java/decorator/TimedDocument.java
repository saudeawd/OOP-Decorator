package main.java.decorator;

public class TimedDocument extends AbstractDecorator {
    
    public TimedDocument(Document document) {
        super(document);
    }

    @Override
    public String parse() {
        long startTime = System.currentTimeMillis();
        System.out.println("Starting document parsing...");
        
        String result = document.parse();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("Parsing completed in " + duration + " ms");
        
        return result;
    }
}