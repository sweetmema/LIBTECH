package library;

import java.sql.*;

public class AuthDAO {
    public int login(String email, String password) throws SQLException {
        String sql = "SELECT u.user_id, " +
                     "CASE WHEN l.librarian_id IS NOT NULL OR a.admin_id IS NOT NULL THEN 2 ELSE 1 END AS role " +
                     "FROM User u " +
                     "LEFT JOIN Student s ON u.user_id=s.user_id " +
                     "LEFT JOIN Librarian l ON u.user_id=l.user_id " +
                     "LEFT JOIN Admin a ON u.user_id=a.user_id " +
                     "WHERE u.email=? AND u.password=? LIMIT 1";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("role");
            }
        }
        return 0;
    }
}
