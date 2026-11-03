/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

/**
 *
 * @author mlew
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public boolean addStudent(String name, String email, String password) throws SQLException {
        String insertUser = "INSERT INTO User (name, email, password) VALUES (?, ?, ?)";
        String insertStudent = "INSERT INTO Student (user_id) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, name);
                ps1.setString(2, email);
                ps1.setString(3, password);
                ps1.executeUpdate();
                ResultSet rs = ps1.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    try (PreparedStatement ps2 = con.prepareStatement(insertStudent)) {
                        ps2.setInt(1, userId);
                        ps2.executeUpdate();
                    }
                }
            }
            con.commit();
            return true;
        }
    }

    public boolean addLibrarian(String name, String email, String password) throws SQLException {
        String insertUser = "INSERT INTO User (name, email, password) VALUES (?, ?, ?)";
        String insertLib  = "INSERT INTO Librarian (user_id) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, name);
                ps1.setString(2, email);
                ps1.setString(3, password);
                ps1.executeUpdate();
                ResultSet rs = ps1.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    try (PreparedStatement ps2 = con.prepareStatement(insertLib)) {
                        ps2.setInt(1, userId);
                        ps2.executeUpdate();
                    }
                }
            }
            con.commit();
            return true;
        }
    }

    public boolean updateMember(int userId, String name, String email) throws SQLException {
        String sql = "UPDATE User SET name = ?, email = ? WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteMember(int userId) throws SQLException {
        String deleteStudent   = "DELETE FROM Student WHERE user_id = ?";
        String deleteLibrarian = "DELETE FROM Librarian WHERE user_id = ?";
        String deleteUser      = "DELETE FROM User WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            for (String sql : new String[]{deleteStudent, deleteLibrarian, deleteUser}) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, userId);
                    ps.executeUpdate();
                }
            }
            con.commit();
            return true;
        }
    }

    public List<User> searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM User WHERE name LIKE ?";
        List<User> results = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"), rs.getString("password")));
            }
        }
        return results;
    }

    public List<User> getAllMembers() throws SQLException {
        String sql = "SELECT * FROM User";
        List<User> results = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"), rs.getString("password")));
            }
        }
        return results;
    }
}