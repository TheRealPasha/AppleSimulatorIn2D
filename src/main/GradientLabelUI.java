package main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

public class GradientLabelUI extends BasicLabelUI {
    Color to; // К цвету (к примеру к белому)
    Color from; // От цвета (к примеру от красного)
    public GradientLabelUI(Color to, Color from) {
        this.to = to;
        this.from = from;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();
        Graphics2D g2d = (Graphics2D) g.create();
        RadialGradientPaint gradient = new RadialGradientPaint(
                w / 2f, h / 2f, w / 1.96f,
                new float[]{0.8f, 1f},
                new Color[]{from, to}
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();
        super.paint(g, c);
    }
}
