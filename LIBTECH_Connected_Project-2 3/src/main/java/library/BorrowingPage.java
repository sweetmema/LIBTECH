package library;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import java.time.LocalDate;

public class BorrowingPage extends JFrame {
    private int activeRole;
    private RoundedTextField txtStudentId, txtBookId;
    private JComboBox<String> cmbBorrowDay, cmbBorrowMonth, cmbBorrowYear;
    private JComboBox<String> cmbReturnDay, cmbReturnMonth, cmbReturnYear;

    public BorrowingPage(int role) {
        this.activeRole = role;
        setTitle("LIBTECH - BOOK BORROWING & RESERVATION");
        setSize(1024, 730); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(30, 30, 50));
        GridBagConstraints lg = new GridBagConstraints();
        JLabel sideHeader = new JLabel("<html><center>BORROWING<br>DEPARTMENT</center></html>");
        sideHeader.setFont(new Font("SansSerif", Font.BOLD, 26));
        sideHeader.setForeground(Color.WHITE);
        leftPanel.add(sideHeader, lg);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(245, 245, 240));
        
        JPanel formWrapper = new JPanel();
        formWrapper.setLayout(new BoxLayout(formWrapper, BoxLayout.Y_AXIS));
        formWrapper.setOpaque(false);

        try {
            java.net.URL imgUrl = getClass().getResource("/library/logo.png");
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
                logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                formWrapper.add(logoLabel);
                formWrapper.add(Box.createVerticalStrut(15)); 
            }
        } catch (Exception e) {}

        Dimension standardFieldSize = new Dimension(360, 32);
        Dimension standardLabelSize = new Dimension(360, 18);

        JLabel lblStudent = new JLabel("Student ID:");
        forceSize(lblStudent, standardLabelSize);
        formWrapper.add(lblStudent);
        
        txtStudentId = new RoundedTextField(18); 
        setupField(txtStudentId, "e.g. 22500011", standardFieldSize); 
        formWrapper.add(txtStudentId);
        formWrapper.add(Box.createVerticalStrut(8));

        JLabel lblBook = new JLabel("Book ID:");
        forceSize(lblBook, standardLabelSize);
        formWrapper.add(lblBook);
        
        txtBookId = new RoundedTextField(18); 
        setupField(txtBookId, "e.g. 104", standardFieldSize); 
        formWrapper.add(txtBookId);
        formWrapper.add(Box.createVerticalStrut(8));

        String[] days = new String[31]; for(int i=0; i<31; i++) days[i] = String.format("%02d", i+1);
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] years = {"2026", "2027", "2028", "2029", "2030"};

        JLabel lblBorrow = new JLabel("Borrow Date:");
        forceSize(lblBorrow, standardLabelSize);
        formWrapper.add(lblBorrow);
        
        JPanel pnlBorrow = new JPanel(new GridLayout(1, 3, 5, 0)); 
        pnlBorrow.setOpaque(false);
        cmbBorrowDay = new JComboBox<>(days); cmbBorrowMonth = new JComboBox<>(months); cmbBorrowYear = new JComboBox<>(years);
        pnlBorrow.add(cmbBorrowDay); pnlBorrow.add(cmbBorrowMonth); pnlBorrow.add(cmbBorrowYear);
        forceSize(pnlBorrow, standardFieldSize);
        formWrapper.add(pnlBorrow);
        formWrapper.add(Box.createVerticalStrut(8));

        JLabel lblReturn = new JLabel("Expected Return Date:");
        forceSize(lblReturn, standardLabelSize);
        formWrapper.add(lblReturn);
        
        JPanel pnlReturn = new JPanel(new GridLayout(1, 3, 5, 0)); 
        pnlReturn.setOpaque(false);
        cmbReturnDay = new JComboBox<>(days); cmbReturnMonth = new JComboBox<>(months); cmbReturnYear = new JComboBox<>(years);
        pnlReturn.add(cmbReturnDay); pnlReturn.add(cmbReturnMonth); pnlReturn.add(cmbReturnYear);
        forceSize(pnlReturn, standardFieldSize);
        formWrapper.add(pnlReturn);
        formWrapper.add(Box.createVerticalStrut(20));

        RoundedButton btnSubmit = new RoundedButton("Submit System Reservation", 22, Color.BLACK, Color.WHITE);
        forceSize(btnSubmit, new Dimension(360, 42));
        formWrapper.add(btnSubmit);
        formWrapper.add(Box.createVerticalStrut(8));

        RoundedButton btnReturn = new RoundedButton("Submit Return Transaction", 22, new Color(30, 30, 50), Color.WHITE);
        forceSize(btnReturn, new Dimension(360, 42));
        formWrapper.add(btnReturn);
        formWrapper.add(Box.createVerticalStrut(8));

        RoundedButton btnBack = new RoundedButton("Return to Dashboard", 20, Color.WHITE, Color.BLACK);
        forceSize(btnBack, new Dimension(360, 38));
        formWrapper.add(btnBack);

        btnSubmit.addActionListener(e -> {
            try {
                String studentText = txtStudentId.getText().trim();
                String bookText = txtBookId.getText().trim();
                if (studentText.isEmpty() || studentText.equals("e.g. 22500011") || bookText.isEmpty() || bookText.equals("e.g. 104")) {
                    JOptionPane.showMessageDialog(this, "Please enter valid Student ID and Book ID.");
                    return;
                }
                int studentId = Integer.parseInt(studentText);
                int bookId = Integer.parseInt(bookText);
                
                java.sql.Connection conn = library.DatabaseConnection.getConnection();
                
                String checkBook = "SELECT is_available FROM Book WHERE book_id = ?";
                java.sql.PreparedStatement checkBkStmt = conn.prepareStatement(checkBook);
                checkBkStmt.setInt(1, bookId);
                java.sql.ResultSet rsBk = checkBkStmt.executeQuery();
                
                if (rsBk.next() && !rsBk.getBoolean("is_available")) {
                    JOptionPane.showMessageDialog(this, "Book is already borrowed and not available!");
                    rsBk.close(); checkBkStmt.close();
                    return;
                }
                rsBk.close(); checkBkStmt.close();

                LocalDate borrowDate = buildDate(cmbBorrowDay, cmbBorrowMonth, cmbBorrowYear);
                LocalDate returnDate = buildDate(cmbReturnDay, cmbReturnMonth, cmbReturnYear);
                
                String insertSql = "INSERT INTO Borrow (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, NULL)";
                java.sql.PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, bookId);
                insertStmt.setDate(3, java.sql.Date.valueOf(borrowDate));
                insertStmt.executeUpdate();
                insertStmt.close();
                
                String updateBookSql = "UPDATE Book SET is_available = FALSE WHERE book_id = ?";
                java.sql.PreparedStatement updateBkStmt = conn.prepareStatement(updateBookSql);
                updateBkStmt.setInt(1, bookId);
                updateBkStmt.executeUpdate();
                updateBkStmt.close();
                
                JOptionPane.showMessageDialog(this, "Borrow transaction saved in database.");
                txtStudentId.setText("");
                txtBookId.setText("");
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage()); 
            }
        });

        btnReturn.addActionListener(e -> {
            try {
                String sId = txtStudentId.getText().trim();
                String bId = txtBookId.getText().trim();
                if (sId.isEmpty() || bId.isEmpty() || sId.equals("e.g. 22500011") || bId.equals("e.g. 104")) {
                    JOptionPane.showMessageDialog(this, "Please enter valid Student ID and Book ID.");
                    return;
                }
                int studentId = Integer.parseInt(sId);
                int bookId = Integer.parseInt(bId);
                
                java.sql.Connection conn = library.DatabaseConnection.getConnection();
                
                String updateSql = "UPDATE Borrow SET return_date = CURDATE() WHERE student_id = ? AND book_id = ? AND return_date IS NULL";
                java.sql.PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, studentId);
                updateStmt.setInt(2, bookId);
                int rows = updateStmt.executeUpdate();
                updateStmt.close();
                
                if (rows > 0) {
                    String updateBookSql = "UPDATE Book SET is_available = TRUE WHERE book_id = ?";
                    java.sql.PreparedStatement updateBkStmt = conn.prepareStatement(updateBookSql);
                    updateBkStmt.setInt(1, bookId);
                    updateBkStmt.executeUpdate();
                    updateBkStmt.close();
                    
                    JOptionPane.showMessageDialog(this, "Book returned successfully! Loan session ended.");
                } else {
                    JOptionPane.showMessageDialog(this, "No active borrow record found for this Student and Book.");
                }
                txtStudentId.setText("");
                txtBookId.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        });
        
        btnBack.addActionListener(e -> { new HomePage(activeRole).setVisible(true); this.dispose(); });

        rightPanel.add(formWrapper);
        
        add(leftPanel); 
        add(rightPanel);
    }

    private LocalDate buildDate(JComboBox<String> dayBox, JComboBox<String> monthBox, JComboBox<String> yearBox) {
        int day = Integer.parseInt(dayBox.getSelectedItem().toString());
        int year = Integer.parseInt(yearBox.getSelectedItem().toString());
        int month = monthBox.getSelectedIndex() + 1;
        return LocalDate.of(year, month, day);
    }

    private void setupField(JTextField f, String hint, Dimension size) {
        forceSize(f, size);
        f.setText(hint); 
        f.setForeground(Color.GRAY);
        f.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) { if (f.getText().equals(hint)) { f.setText(""); f.setForeground(Color.BLACK); } }
            public void focusLost(FocusEvent e) { if (f.getText().isEmpty()) { f.setText(hint); f.setForeground(Color.GRAY); } }
        });
    }

    private void forceSize(JComponent comp, Dimension size) {
        comp.setPreferredSize(size);
        comp.setMinimumSize(size);
        comp.setMaximumSize(size);
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}