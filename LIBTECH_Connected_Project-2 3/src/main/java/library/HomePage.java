package library;

import java.awt.*;
import javax.swing.*;

public class HomePage extends JFrame {
    private int activeRole;

    public HomePage(int role) {
        this.activeRole = role;
        setTitle("LIBTECH - DASHBOARD MENU");
        setSize(1024, 680); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 240));
        
        JPanel contentContainer = new JPanel();
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));
        contentContainer.setOpaque(false);

        try {
            java.net.URL imgUrl = getClass().getResource("/library/logo.png");
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(240, 240, Image.SCALE_SMOOTH); 
                JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
                logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentContainer.add(logoLabel);
                contentContainer.add(Box.createVerticalStrut(20)); 
            }
        } catch (Exception e) {
            System.out.println("Logo loading error.");
        }

        RoundedButton btnBorrow = createMenuButton("Borrow book");
        RoundedButton btnReview = createMenuButton("Review book");
        RoundedButton btnLogout = createMenuButton("Sign Out");

        btnBorrow.addActionListener(e -> { new BorrowingPage(activeRole).setVisible(true); this.dispose(); });
        btnReview.addActionListener(e -> { new ReviewPage(activeRole).setVisible(true); this.dispose(); });
        btnLogout.addActionListener(e -> { new LoginForm().setVisible(true); this.dispose(); });

        contentContainer.add(btnBorrow); 
        contentContainer.add(Box.createVerticalStrut(10)); 
        contentContainer.add(btnReview); 
        contentContainer.add(Box.createVerticalStrut(10));

        if (activeRole == 2) {
            RoundedButton btnManage = createMenuButton("Manage books");
            btnManage.addActionListener(e -> { new ManageBooksPage(activeRole).setVisible(true); this.dispose(); });
            contentContainer.add(btnManage); 
            contentContainer.add(Box.createVerticalStrut(10));
            
            RoundedButton btnReports = createMenuButton("System Reports");
            btnReports.addActionListener(e -> { new ReportForm(activeRole).setVisible(true); this.dispose(); });
            contentContainer.add(btnReports); 
            contentContainer.add(Box.createVerticalStrut(10));
        }

        contentContainer.add(btnLogout);
        
        centerPanel.add(contentContainer);
        add(centerPanel, BorderLayout.CENTER);

        JPanel blueBottomPanel = new JPanel();
        blueBottomPanel.setBackground(new Color(30, 30, 50));
        blueBottomPanel.setPreferredSize(new Dimension(1024, 40)); 
        add(blueBottomPanel, BorderLayout.SOUTH);
    }

    private RoundedButton createMenuButton(String label) {
        RoundedButton button = new RoundedButton(label, 22, Color.BLACK, Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Dimension standardSize = new Dimension(360, 50); 
        button.setMinimumSize(standardSize);
        button.setMaximumSize(standardSize);
        button.setPreferredSize(standardSize);
        
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }
}