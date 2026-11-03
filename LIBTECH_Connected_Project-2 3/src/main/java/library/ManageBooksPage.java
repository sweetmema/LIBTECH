/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

/**
 *
 * @author mlew
 */
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.List;

public class ManageBooksPage extends JFrame {
    private int activeRole;
    private RoundedTextField txtBookId, txtTitle, txtAuthor;
    private JComboBox<String> comboCategory, comboYear;

    public ManageBooksPage(int role) {
        this.activeRole = role;
        setTitle("LIBTECH - LIBRARIAN CONTROL PANEL");
        setSize(1024, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // --- LEFT BLUE PANEL ---
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(30, 30, 50));
        GridBagConstraints lg = new GridBagConstraints();
        JLabel sideHeader = new JLabel("<html><center>CATALOGUE<br>MANAGEMENT</center></html>");
        sideHeader.setFont(new Font("SansSerif", Font.BOLD, 26));
        sideHeader.setForeground(Color.WHITE);
        leftPanel.add(sideHeader, lg);

        // --- RIGHT PANEL FORM ---
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(245, 245, 240));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;

        // LOGO
        try {
            java.net.URL imgUrl = getClass().getResource("/library/logo.png");
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
                logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
                g.anchor = GridBagConstraints.CENTER;
                g.insets = new Insets(5, 12, 18, 12);
                rightPanel.add(logoLabel, g);
            }
        } catch (Exception e) {}

        g.insets = new Insets(4, 12, 4, 12);

        g.gridwidth = 1; g.gridx = 0; g.gridy = 1; rightPanel.add(new JLabel("Book ID:"), g);
        g.gridx = 1; txtBookId = new RoundedTextField(18); setupField(txtBookId, "For update/delete only"); rightPanel.add(txtBookId, g);

        g.gridx = 0; g.gridy = 2; rightPanel.add(new JLabel("Book Title:"), g);
        g.gridx = 1; txtTitle = new RoundedTextField(18); setupField(txtTitle, "e.g. Intro to Algorithms"); rightPanel.add(txtTitle, g);

        g.gridx = 0; g.gridy = 3; rightPanel.add(new JLabel("Author Name:"), g);
        g.gridx = 1; txtAuthor = new RoundedTextField(18); setupField(txtAuthor, "e.g. Thomas H. Cormen"); rightPanel.add(txtAuthor, g);

        g.gridx = 0; g.gridy = 4; rightPanel.add(new JLabel("Category:"), g);
        g.gridx = 1;
        String[] categories = {"Programming", "Database", "Networking", "Cyber Security", "Artificial Intelligence", "Engineering", "Self Development", "Novel", "Business", "Mathematics"};
        comboCategory = new JComboBox<>(categories); comboCategory.setPreferredSize(new Dimension(280, 32));
        rightPanel.add(comboCategory, g);

        g.gridx = 0; g.gridy = 5; rightPanel.add(new JLabel("Year:"), g);
        g.gridx = 1;
        String[] years = {"2026", "2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2006", "2005", "2001", "1999", "1997", "1996", "1994", "1989", "1988", "1851"};
        comboYear = new JComboBox<>(years); comboYear.setPreferredSize(new Dimension(280, 32));
        rightPanel.add(comboYear, g);

        // --- أزرار Insert / Modify / Delete ---
        g.gridx = 0; g.gridy = 6; g.gridwidth = 2; g.insets = new Insets(18, 12, 4, 12);
        JPanel panelActionGroup = new JPanel(new GridLayout(1, 3, 8, 0)); panelActionGroup.setOpaque(false);
        RoundedButton btnAdd    = new RoundedButton("Insert", 22, Color.BLACK, Color.WHITE);
        RoundedButton btnUpdate = new RoundedButton("Modify", 22, Color.BLACK, Color.WHITE);
        RoundedButton btnDelete = new RoundedButton("Delete", 22, Color.BLACK, Color.WHITE);
        panelActionGroup.add(btnAdd); panelActionGroup.add(btnUpdate); panelActionGroup.add(btnDelete);
        panelActionGroup.setPreferredSize(new Dimension(280, 42));
        rightPanel.add(panelActionGroup, g);

        // --- زر Export CSV ---
        g.gridy = 7; g.insets = new Insets(4, 12, 4, 12);
        RoundedButton btnExport = new RoundedButton("Export Books to CSV", 22, new Color(30, 30, 50), Color.WHITE);
        btnExport.setPreferredSize(new Dimension(280, 38));
        rightPanel.add(btnExport, g);

        // --- زر الرجوع ---
        g.gridy = 8; g.insets = new Insets(4, 12, 4, 12);
        RoundedButton btnBack = new RoundedButton("Return to Dashboard Menu", 20, Color.WHITE, Color.BLACK);
        btnBack.setPreferredSize(new Dimension(280, 38));
        rightPanel.add(btnBack, g);

        // --- Action Listeners ---
        btnAdd.addActionListener(e -> {
            try {
                boolean ok = new BookDAO().addBook(clean(txtTitle), clean(txtAuthor), comboCategory.getSelectedItem().toString(), Integer.parseInt(comboYear.getSelectedItem().toString()));
                JOptionPane.showMessageDialog(this, ok ? "Book inserted in database." : "Insert failed.");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage()); }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(clean(txtBookId));
                boolean ok = new BookDAO().updateBook(id, clean(txtTitle), clean(txtAuthor), comboCategory.getSelectedItem().toString(), Integer.parseInt(comboYear.getSelectedItem().toString()));
                JOptionPane.showMessageDialog(this, ok ? "Book updated in database." : "Book ID not found.");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage()); }
        });

        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(clean(txtBookId));
                boolean ok = new BookDAO().deleteBook(id);
                JOptionPane.showMessageDialog(this, ok ? "Book deleted from database." : "Book ID not found.");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage()); }
        });

        btnExport.addActionListener(e -> exportBooksToCSV());

        btnBack.addActionListener(e -> { new HomePage(activeRole).setVisible(true); this.dispose(); });

        add(leftPanel);
        add(rightPanel);
    }

    private void exportBooksToCSV() {
        // اختيار مكان الحفظ
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Books as CSV");
        fileChooser.setSelectedFile(new File("books.csv"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection != JFileChooser.APPROVE_OPTION) return;

        File fileToSave = fileChooser.getSelectedFile();
        // تأكد أن الامتداد .csv
        if (!fileToSave.getName().endsWith(".csv")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
        }

        // جلب البيانات من قاعدة البيانات وكتابتها في الملف
        String sql = "SELECT book_id, title, author, year, category, is_available FROM Book";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             PrintWriter writer = new PrintWriter(new FileWriter(fileToSave))) {

            // رأس الجدول
            writer.println("Book ID,Title,Author,Year,Category,Available");

            // البيانات
            while (rs.next()) {
                writer.printf("%d,\"%s\",\"%s\",%d,\"%s\",%s%n",
                    rs.getInt("book_id"),
                    rs.getString("title").replace("\"", "\"\""),
                    rs.getString("author").replace("\"", "\"\""),
                    rs.getInt("year"),
                    rs.getString("category"),
                    rs.getBoolean("is_available") ? "Yes" : "No"
                );
            }

            JOptionPane.showMessageDialog(this,
                "Books exported successfully!\nSaved to: " + fileToSave.getAbsolutePath(),
                "Export Successful", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Export failed: " + ex.getMessage(),
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String clean(JTextField f) { return f.getText().trim(); }

    private void setupField(JTextField f, String hint) {
        f.setPreferredSize(new Dimension(280, 32)); f.setText(hint); f.setForeground(Color.GRAY);
        f.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) { if (f.getText().equals(hint)) { f.setText(""); f.setForeground(Color.BLACK); } }
            public void focusLost(FocusEvent e) { if (f.getText().isEmpty()) { f.setText(hint); f.setForeground(Color.GRAY); } }
        });
    }
}