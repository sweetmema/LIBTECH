package library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public boolean addBook(String title, String author, String category, int year) throws SQLException {
        String sql = "INSERT INTO Book (title, author, year, category, is_available) VALUES (?, ?, ?, ?, TRUE)";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, year);
            ps.setString(4, category);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateBook(int bookId, String title, String author, String category, int year) throws SQLException {
        String sql = "UPDATE Book SET title=?, author=?, year=?, category=? WHERE book_id=?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, year);
            ps.setString(4, category);
            ps.setInt(5, bookId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM Book WHERE book_id=?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Book> searchByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        List<Book> results = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("category"),
                        rs.getBoolean("is_available")
                    ));
                }
            }
        }
        return results;
    }
}
