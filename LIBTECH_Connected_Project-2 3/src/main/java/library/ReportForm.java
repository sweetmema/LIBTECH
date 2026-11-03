package library;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

public class ReportForm extends JFrame {
    private int activeRole;
    private JLabel totalBooksLabel;
    private JLabel totalStudentsLabel;
    private JLabel borrowedBooksLabel;
    private ReportDAO reportDAO;

    public ReportForm(int role) {
        this.activeRole = role;
        this.reportDAO = new ReportDAO();
        
        setTitle("LIBTECH - SYSTEM REPORTS & DASHBOARD");
        setSize(1024, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(30, 30, 50));
        GridBagConstraints lg = new GridBagConstraints();
        JLabel sideHeader = new JLabel("<html><center>REPORTS &<br>STATISTICS</center></html>");
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
                formWrapper.add(Box.createVerticalStrut(20));
            }
        } catch (Exception e) {}

        Dimension cardSize = new Dimension(360, 40);

        totalBooksLabel = createDashboardCard("Total Books in Library: Loading...", cardSize);
        totalStudentsLabel = createDashboardCard("Registered Students: Loading...", cardSize);
        borrowedBooksLabel = createDashboardCard("Currently Borrowed Books: Loading...", cardSize);

        formWrapper.add(totalBooksLabel);
        formWrapper.add(Box.createVerticalStrut(10));
        formWrapper.add(totalStudentsLabel);
        formWrapper.add(Box.createVerticalStrut(10));
        formWrapper.add(borrowedBooksLabel);
        formWrapper.add(Box.createVerticalStrut(25));

        RoundedButton btnRefresh = new RoundedButton("Refresh Statistics", 22, Color.BLACK, Color.WHITE);
        forceSize(btnRefresh, new Dimension(360, 42));
        formWrapper.add(btnRefresh);
        formWrapper.add(Box.createVerticalStrut(8));

        RoundedButton btnBack = new RoundedButton("Return to Dashboard", 20, Color.WHITE, Color.BLACK);
        forceSize(btnBack, new Dimension(360, 38));
        formWrapper.add(btnBack);

        btnRefresh.addActionListener(e -> updateStatistics());
        btnBack.addActionListener(e -> { new HomePage(activeRole).setVisible(true); this.dispose(); });

        rightPanel.add(formWrapper);
        add(leftPanel); 
        add(rightPanel);

        updateStatistics();
    }

    private JLabel createDashboardCard(String text, Dimension size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(30, 30, 50));
        label.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 50), 2));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        forceSize(label, size);
        return label;
    }

    private void forceSize(JComponent comp, Dimension size) {
        comp.setPreferredSize(size);
        comp.setMinimumSize(size);
        comp.setMaximumSize(size);
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void updateStatistics() {
        try {
            int books = reportDAO.getTotalBooks();
            int students = reportDAO.getTotalStudents();
            int borrowed = reportDAO.getBorrowedBooksCount();

            totalBooksLabel.setText("Total Books in Library: " + books);
            totalStudentsLabel.setText("Registered Students: " + students);
            borrowedBooksLabel.setText("Currently Borrowed Books: " + borrowed);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading statistics: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ReportForm(2).setVisible(true);
    }
}