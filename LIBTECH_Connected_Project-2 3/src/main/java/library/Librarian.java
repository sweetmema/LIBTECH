/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

/**
 *
 * @author mlew
 */
public class Librarian extends User {
    private int librarianId;

    public Librarian(int userId, String name, String email, String password, int librarianId) {
        super(userId, name, email, password);
        this.librarianId = librarianId;
    }

    public int getLibrarianId() { return librarianId; }
}