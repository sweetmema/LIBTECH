/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

/**
 *
 * @author mlew
 */
public class Student extends User {
    private int studentId;

    public Student(int userId, String name, String email, String password, int studentId) {
        super(userId, name, email, password);
        this.studentId = studentId;
    }

    public int getStudentId() { return studentId; }
}
