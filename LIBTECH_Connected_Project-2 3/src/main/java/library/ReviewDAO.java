package library;

import java.sql.*;

public class ReviewDAO {
    public boolean addReview(int studentId, int bookId, int rating, String comment) throws SQLException {
        String sql = "INSERT INTO Review (student_id, book_id, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, bookId);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            return ps.executeUpdate() > 0;
        }
    }
}
