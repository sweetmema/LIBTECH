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
import java.util.Random;

public class LoginForm extends JFrame {
    private RoundedTextField usernameField;
    private JPasswordField passwordField;
    private RoundedButton loginButton;

    public LoginForm() {
        setTitle("LIBTECH SYSTEM - LOGIN");
        setSize(1024, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // --- LEFT PANEL ---
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                // خلفية
                g2.setColor(new Color(30, 30, 50));
                g2.fillRect(0, 0, panelWidth, panelHeight);

                // ألوان الكتب
                Color[] bookColors = {
                    new Color(140, 70, 70),
                    new Color(80, 100, 140),
                    new Color(100, 130, 90),
                    new Color(160, 120, 60),
                    new Color(120, 80, 130),
                    new Color(70, 120, 120),
                    new Color(150, 90, 60),
                    new Color(90, 90, 150),
                };

                int shelfCount = 5;
                int shelfHeight = panelHeight / shelfCount;
                int bookSpacing = 4;
                Random rand = new Random(42);

                // رفين فوق (0,1) ورفين تحت (3,4) - المنتصف (2) فاضي للنص
                int[] shelfRows = {0, 1, 3, 4};

                for (int row : shelfRows) {
                    int shelfY = row * shelfHeight;
                    int baseY = shelfY + shelfHeight - 18;

                    // خط الرف
                    g2.setColor(new Color(60, 55, 80));
                    g2.fillRect(0, baseY, panelWidth, 8);

                    // رسم الكتب
                    int x = 10;
                    while (x < panelWidth - 10) {
                        int bookWidth = 18 + rand.nextInt(18);
                        int bookHeight = 55 + rand.nextInt(45);
                        int bookY = baseY - bookHeight;

                        Color base = bookColors[rand.nextInt(bookColors.length)];

                        g2.setColor(base);
                        g2.fillRect(x, bookY, bookWidth, bookHeight);

                        g2.setColor(base.darker());
                        g2.fillRect(x, bookY, 4, bookHeight);

                        g2.setColor(new Color(255, 255, 255, 15));
                        g2.fillRect(x + 4, bookY, bookWidth - 4, bookHeight);

                        x += bookWidth + bookSpacing;
                    }
                }
            }
        };
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(512, 680));

        GridBagConstraints lg = new GridBagConstraints();
        lg.gridx = 0; lg.gridy = 0;
        lg.anchor = GridBagConstraints.CENTER;
        lg.insets = new Insets(10, 10, 10, 10);

        JLabel systemTitle = new JLabel("<html><center>Welcome to LIBTECH<br>Manage your library smarter</center></html>");
        systemTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        systemTitle.setForeground(Color.WHITE);
        systemTitle.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(systemTitle, lg);

        // --- RIGHT FORM PANEL ---
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
                g.insets = new Insets(5, 15, 25, 15);
                rightPanel.add(logoLabel, g);
            }
        } catch (Exception e) {
            System.out.println("Logo loading error.");
        }

        g.insets = new Insets(6, 15, 6, 15);

        g.gridwidth = 1; g.gridx = 0; g.gridy = 1;
        rightPanel.add(new JLabel("E-mail:"), g);

        g.gridy = 2;
        usernameField = new RoundedTextField(20);
        setupPlaceholder(usernameField, "Enter your email");
        rightPanel.add(usernameField, g);

        g.gridy = 3;
        rightPanel.add(new JLabel("Password:"), g);

        g.gridy = 4;
        passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(195, 160, 90));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
            @Override
            public Insets getInsets() { return new Insets(5, 12, 5, 12); }
        };
        passwordField.setOpaque(false);
        passwordField.setBackground(Color.WHITE);
        setupPasswordPlaceholder(passwordField, "Password");
        rightPanel.add(passwordField, g);

        g.gridy = 5; g.gridwidth = 2;
        g.insets = new Insets(20, 15, 4, 15);
        loginButton = new RoundedButton("Login", 20, Color.BLACK, Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(280, 42));
        rightPanel.add(loginButton, g);

        loginButton.addActionListener(e -> {
            String email = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            try {
                int role = new AuthDAO().login(email, pass);
                if (role > 0) {
                    new HomePage(role).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(leftPanel); add(rightPanel);
    }

    private void setupPlaceholder(JTextField field, String hint) {
        field.setPreferredSize(new Dimension(280, 40)); field.setText(hint); field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) { if (field.getText().equals(hint)) { field.setText(""); field.setForeground(Color.BLACK); } }
            public void focusLost(FocusEvent e) { if (field.getText().isEmpty()) { field.setText(hint); field.setForeground(Color.GRAY); } }
        });
    }

    private void setupPasswordPlaceholder(JPasswordField field, String hint) {
        field.setPreferredSize(new Dimension(280, 40)); field.setEchoChar((char) 0); field.setText(hint); field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) { if (new String(field.getPassword()).equals(hint)) { field.setText(""); field.setEchoChar('•'); field.setForeground(Color.BLACK); } }
            public void focusLost(FocusEvent e) { if (field.getPassword().length == 0) { field.setText(hint); field.setEchoChar((char) 0); field.setForeground(Color.GRAY); } }
        });
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true)); }
}