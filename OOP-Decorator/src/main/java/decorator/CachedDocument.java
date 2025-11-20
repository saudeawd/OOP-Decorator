package main.java.decorator;

import java.sql.*;

public class CachedDocument extends AbstractDecorator {
    private static final String DB_URL = "jdbc:sqlite:document_cache.db";
    private String documentId;

    public CachedDocument(Document document, String documentId) {
        super(document);
        this.documentId = documentId;
        initDatabase();
    }

    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            String sql = "CREATE TABLE IF NOT EXISTS document_cache (" +
                        "id TEXT PRIMARY KEY," +
                        "content TEXT NOT NULL," +
                        "cached_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(sql);
            System.out.println("Database initialized for caching documents.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    @Override
    public String parse() {
        String cached = getFromCache();
        if (cached != null) {
            System.out.println("Result retrieved from cache for document: " + documentId);
            return cached;
        }

        System.out.println("Document not found in cache, parsing...");
        String result = document.parse();
        saveToCache(result);
        
        return result;
    }

    private String getFromCache() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT content FROM document_cache WHERE id = ?")) {
            
            pstmt.setString(1, documentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("content");
            }
        } catch (SQLException e) {
            System.err.println("Error getting from cache: " + e.getMessage());
        }
        return null;
    }

    private void saveToCache(String content) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT OR REPLACE INTO document_cache (id, content) VALUES (?, ?)")) {
            
            pstmt.setString(1, documentId);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
            
            System.out.println("Result cached for document: " + documentId);
        } catch (SQLException e) {
            System.err.println("Error saving to cache: " + e.getMessage());
        }
    }

    public static void clearCache() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM document_cache");
            System.out.println("Cache cleared.");
        } catch (SQLException e) {
            System.err.println("Error clearing cache: " + e.getMessage());
        }
    }
}