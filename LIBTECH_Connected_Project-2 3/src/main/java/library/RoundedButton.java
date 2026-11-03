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
import javax.swing.*;

public class RoundedButton extends JButton {
    private int radius;
    private Color bgColor;

    public RoundedButton(String text, int radius, Color bgColor, Color fgColor) {
        super(text);
        this.radius = radius;
        this.bgColor = bgColor;
        setForeground(fgColor);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isArmed()) {
            g2.setColor(bgColor.darker());
        } else if (getModel().isRollover()) {
            g2.setColor(bgColor.brighter());
        } else {
            g2.setColor(bgColor);
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}