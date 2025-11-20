package test.java.decorator;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Додайте ці імпорти
import main.java.decorator.*;

class DecoratorTests {

    @Test
    @DisplayName("Test: Basic cactus flower price")
    void testCactusFlowerPrice() {
        CactusFlower cactus = new CactusFlower();
        assertEquals(50.0, cactus.price(), 0.01);
        assertEquals("Cactus flower", cactus.getDescription());
    }

    @Test
    @DisplayName("Test: Basic romashka flower price")
    void testRomashkaFlowerPrice() {
        RomashkaFlower romashka = new RomashkaFlower();
        assertEquals(30.0, romashka.price(), 0.01);
        assertEquals("Romashka flower", romashka.getDescription());
    }

    @Test
    @DisplayName("Test: Flower bucket with multiple flowers")
    void testFlowerBucket() {
        FlowerBucket bucket = new FlowerBucket();
        bucket.addFlower(new CactusFlower());
        bucket.addFlower(new RomashkaFlower());
        
        assertEquals(80.0, bucket.price(), 0.01);
        assertTrue(bucket.getDescription().contains("Flower Bucket"));
    }

    @Test
    @DisplayName("Test: PaperDecorator adds 13 to price")
    void testPaperDecorator() {
        Item flower = new CactusFlower();
        Item decorated = new PaperDecorator(flower);
        
        assertEquals(63.0, decorated.price(), 0.01);
        assertTrue(decorated.getDescription().contains("Paper wrapping"));
    }

    @Test
    @DisplayName("Test: BasketDecorator adds 4 to price")
    void testBasketDecorator() {
        Item flower = new RomashkaFlower();
        Item decorated = new BasketDecorator(flower);
        
        assertEquals(34.0, decorated.price(), 0.01);
        assertTrue(decorated.getDescription().contains("Basket wrapping"));
    }

    @Test
    @DisplayName("Test: RibbonDecorator adds 40 to price")
    void testRibbonDecorator() {
        Item flower = new CactusFlower();
        Item decorated = new RibbonDecorator(flower);
        
        assertEquals(90.0, decorated.price(), 0.01);
        assertTrue(decorated.getDescription().contains("Ribbon"));
    }

    @Test
    @DisplayName("Test: Multiple decorators (Paper + Basket + Ribbon)")
    void testMultipleDecorators() {
        FlowerBucket bucket = new FlowerBucket();
        bucket.addFlower(new CactusFlower());
        bucket.addFlower(new RomashkaFlower());

        Item decorated = new PaperDecorator(bucket);
        decorated = new BasketDecorator(decorated);
        decorated = new RibbonDecorator(decorated);
        
        assertEquals(137.0, decorated.price(), 0.01);
        
        String desc = decorated.getDescription();
        assertTrue(desc.contains("Paper wrapping"));
        assertTrue(desc.contains("Basket wrapping"));
        assertTrue(desc.contains("Ribbon"));
    }

    @Test
    @DisplayName("Test: Decorator order does not affect price")
    void testDecoratorOrderDoesNotAffectPrice() {
        Item flower1 = new CactusFlower();
        Item decorated1 = new PaperDecorator(new BasketDecorator(flower1));
        
        Item flower2 = new CactusFlower();
        Item decorated2 = new BasketDecorator(new PaperDecorator(flower2));
        
        assertEquals(decorated1.price(), decorated2.price(), 0.01);
    }

    // DOCUMENT TESTS

    @Test
    @DisplayName("Test: TimedDocument executes parsing")
    void testTimedDocument() {
        // Mock document
        Document mockDoc = mock(Document.class);
        when(mockDoc.parse()).thenReturn("Parsed content");
        
        Document timedDoc = new TimedDocument(mockDoc);
        String result = timedDoc.parse();
        
        assertEquals("Parsed content", result);
        verify(mockDoc, times(1)).parse();
    }

    @Test
    @DisplayName("Test: AbstractDecorator delegates calls")
    void testAbstractDecorator() {
        Document mockDoc = mock(Document.class);
        when(mockDoc.parse()).thenReturn("Test content");
        
        AbstractDecorator decorator = new AbstractDecorator(mockDoc) {};
        String result = decorator.parse();
        
        assertEquals("Test content", result);
        verify(mockDoc, times(1)).parse();
    }

    @Test
    @DisplayName("Test: CachedDocument saves result")
    void testCachedDocumentSavesResult() {
        CachedDocument.clearCache();

        Document mockDoc = mock(Document.class);
        when(mockDoc.parse()).thenReturn("Cached content");
        
        CachedDocument cachedDoc = new CachedDocument(mockDoc, "test-doc-1");

        String result1 = cachedDoc.parse();
        assertEquals("Cached content", result1);
        verify(mockDoc, times(1)).parse();
        
        String result2 = cachedDoc.parse();
        assertEquals("Cached content", result2);
        verify(mockDoc, times(1)).parse(); // Still 1 time!
    }

    @Test
    @DisplayName("Test: TimedDocument + CachedDocument combination")
    void testTimedAndCachedDocument() {
        CachedDocument.clearCache();
        
        Document mockDoc = mock(Document.class);
        when(mockDoc.parse()).thenReturn("Combined test");
        
        Document cachedDoc = new CachedDocument(mockDoc, "test-doc-2");
        Document timedCachedDoc = new TimedDocument(cachedDoc);
        
        String result = timedCachedDoc.parse();
        
        assertEquals("Combined test", result);
        verify(mockDoc, times(1)).parse();
    }

    @Test
    @DisplayName("Test: Different documents have separate cache")
    void testDifferentDocumentsHaveSeparateCache() {
        CachedDocument.clearCache();
        
        Document mockDoc1 = mock(Document.class);
        when(mockDoc1.parse()).thenReturn("Document 1");
        
        Document mockDoc2 = mock(Document.class);
        when(mockDoc2.parse()).thenReturn("Document 2");
        
        CachedDocument cached1 = new CachedDocument(mockDoc1, "doc-1");
        CachedDocument cached2 = new CachedDocument(mockDoc2, "doc-2");
        
        String result1 = cached1.parse();
        String result2 = cached2.parse();
        
        assertEquals("Document 1", result1);
        assertEquals("Document 2", result2);
        assertNotEquals(result1, result2);
    }

    @AfterAll
    static void cleanup() {
        CachedDocument.clearCache();
    }
}