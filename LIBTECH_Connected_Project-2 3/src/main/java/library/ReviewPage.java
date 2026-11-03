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

public class ReviewPage extends JFrame {
    private int activeRole;
    private RoundedTextField txtStudentId, txtBookId;
    private JSlider sliderRating;
    private JTextArea txtComment;

    public ReviewPage(int role) {
        this.activeRole = role;
        setTitle("LIBTECH - CUSTOMER REVIEW MANAGEMENT");
        setSize(1024, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // --- LEFT BLUE PANEL ---
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(30, 30, 50));
        GridBagConstraints lg = new GridBagConstraints();
        JLabel sideHeader = new JLabel("<html><center>FEEDBACK<br>SYSTEM</center></html>");
        sideHeader.setFont(new Font("SansSerif", Font.BOLD, 26));
        sideHeader.setForeground(Color.WHITE);
        leftPanel.add(sideHeader, lg);

        // --- RIGHT PANEL FORM ---
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(245, 245, 240));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;

        // MATCHING 260x260 LOGO
        try {
            java.net.URL imgUrl = getClass().getResource("/library/logo.png");
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
                logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
                g.anchor = GridBagConstraints.CENTER;
                g.insets = new Insets(5, 15, 20, 15);
                rightPanel.add(logoLabel, g);
            }
        } catch (Exception e) {}

        g.insets = new Insets(5, 15, 5, 15);

        // FORM INPUTS (Subheading Removed)
        g.gridwidth = 1; g.gridx = 0; g.gridy = 1; rightPanel.add(new JLabel("Student ID:"), g);
        g.gridx = 1; txtStudentId = new RoundedTextField(18); txtStudentId.setPreferredSize(new Dimension(280, 32));
        setupFieldPlaceholder(txtStudentId, "Enter student id"); rightPanel.add(txtStudentId, g);

        g.gridx = 0; g.gridy = 2; rightPanel.add(new JLabel("Book ID:"), g);
        g.gridx = 1; txtBookId = new RoundedTextField(18); txtBookId.setPreferredSize(new Dimension(280, 32));
        setupFieldPlaceholder(txtBookId, "Enter catalog index"); rightPanel.add(txtBookId, g);

        g.gridx = 0; g.gridy = 3; rightPanel.add(new JLabel("Score:"), g);
        g.gridx = 1;
        sliderRating = new JSlider(0, 10, 6); sliderRating.setMajorTickSpacing(2); sliderRating.setMinorTickSpacing(1);
        sliderRating.setPaintTicks(true); sliderRating.setSnapToTicks(true); sliderRating.setBackground(new Color(245, 245, 240));
        java.util.Hashtable<Integer, JLabel> labelTable = new java.util.Hashtable<>();
        labelTable.put(0, new JLabel("0")); labelTable.put(2, new JLabel("1")); labelTable.put(4, new JLabel("2"));
        labelTable.put(6, new JLabel("3")); labelTable.put(8, new JLabel("4")); labelTable.put(10, new JLabel("5"));
        sliderRating.setLabelTable(labelTable); sliderRating.setPaintLabels(true); rightPanel.add(sliderRating, g);

        g.gridx = 0; g.gridy = 4; rightPanel.add(new JLabel("Review:"), g);
        g.gridx = 1;
        txtComment = new JTextArea(2, 20) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground()); g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18); g2.dispose(); super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(195, 160, 90)); g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18); g2.dispose();
            }
        };
        txtComment.setOpaque(false); txtComment.setBackground(Color.WHITE); txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true); txtComment.setMargin(new Insets(5, 12, 5, 12));
        setupAreaPlaceholder(txtComment, "Share your observations...");
        
        JScrollPane scroll = new JScrollPane(txtComment); scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false); scroll.getViewport().setOpaque(false); rightPanel.add(scroll, g);

        g.gridx = 0; g.gridy = 5; g.gridwidth = 2; g.insets = new Insets(18, 15, 4, 15);
        RoundedButton btnPost = new RoundedButton("Publish Evaluation Entry", 22, Color.BLACK, Color.WHITE);
        btnPost.setPreferredSize(new Dimension(280, 42)); rightPanel.add(btnPost, g);

        g.gridy = 6; g.insets = new Insets(4, 15, 4, 15);
        RoundedButton btnBack = new RoundedButton("Dismiss to Core Menu", 20, Color.WHITE, Color.BLACK);
        btnBack.setPreferredSize(new Dimension(280, 38)); rightPanel.add(btnBack, g);

        btnPost.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(txtStudentId.getText().trim());
                int bookId = Integer.parseInt(txtBookId.getText().trim());
                int rating = sliderRating.getValue() / 2;
                boolean ok = new ReviewDAO().addReview(studentId, bookId, rating, txtComment.getText().trim());
                JOptionPane.showMessageDialog(this, ok ? "Review saved in database." : "Review failed.");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage()); }
        });
        btnBack.addActionListener(e -> { new HomePage(activeRole).setVisible(true); this.dispose(); });

        add(leftPanel); add(rightPanel);
    }

    private void setupFieldPlaceholder(JTextField f, String hint) {
        f.setText(hint); f.setForeground(Color.GRAY);
        f.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) { if (f.getText().equals(hint)) { f.setText(""); f.setForeground(Color.BLACK); } }
            public void focusLost(FocusEvent e) { if (f.getText().isEmpty()) { f.setText(hint); f.setForeground(Color.GRAY); } }
        });
    }

    private void setupAreaPlaceholder(JTextArea a, String hint) {
        a.setText(hint); a.setForeground(Color.GRAY);
        a.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) { if (a.getText().equals(hint)) { a.setText(""); a.setForeground(Color.BLACK); } }
            public void focusLost(FocusEvent e) { if (a.getText().isEmpty()) { a.setText(hint); a.setForeground(Color.GRAY); } }
        });
    }
}