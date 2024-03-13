package main;

import javax.swing.*;
import java.awt.*;

public class MapsButton extends JButton {
    private int arcWidth;
    private int arcHeight;
    public MapsButton(String label, int arcWidth, int arcHeight) {
        super(label);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setOpaque(false);
    }
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        g2d.setColor(getForeground());
        g2d.drawString(getText(), getWidth() / 2 - (getFontMetrics(getFont()).stringWidth(getText()) / 2), getHeight() / 2 + (getFontMetrics(getFont()).getAscent() / 2) - (getFontMetrics(getFont()).getDescent() / 2));
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getForeground());
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        g2d.dispose();
    }
}
