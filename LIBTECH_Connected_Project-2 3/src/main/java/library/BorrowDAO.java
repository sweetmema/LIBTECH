package library;

import java.sql.*;
import java.time.LocalDate;

public class BorrowDAO {
    public boolean borrowBook(int studentId, int bookId, LocalDate borrowDate, LocalDate returnDate) throws SQLException {
        String check = "SELECT is_available FROM Book WHERE book_id=?";
        String insert = "INSERT INTO Borrow (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        String update = "UPDATE Book SET is_available=FALSE WHERE book_id=?";
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement(check)) {
                    ps.setInt(1, bookId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next() || !rs.getBoolean("is_available")) {
                            con.rollback();
                            return false;
                        }
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(insert)) {
                    ps.setInt(1, studentId);
                    ps.setInt(2, bookId);
                    ps.setDate(3, Date.valueOf(borrowDate));
                    if (returnDate == null) ps.setNull(4, Types.DATE); else ps.setDate(4, Date.valueOf(returnDate));
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = con.prepareStatement(update)) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
