package main;

import data.Data;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.Locale;

import static main.Panel.panel;

public class ResourcesPanel extends JPanel {
    public static JLabel appleLabel = new JLabel();

    public ResourcesPanel() {
        int width = (int) (panel.SCREEN_WIDTH/2.2);
        int height = panel.SCREEN_HEIGHT/9;
        int fontSize = 70;
        this.setBounds((panel.SCREEN_WIDTH - width) / 2, 0, width, height);
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(Color.decode("#FF726D"), 7));

        this.setLayout(new GridLayout(0,1));

        appleLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        appleLabel.setForeground(Color.BLACK);
        appleLabel.setVerticalAlignment(JLabel.CENTER);
        appleLabel.setHorizontalAlignment(JLabel.CENTER);

        updateAppleLabel();

        this.add(appleLabel);
    }

    public static void updateAppleLabel() {
        if (Data.apple >= 1000000000) { // Миллиард (B)
            double value = Data.apple / 1000000000.0;
            appleLabel.setText(String.format(Locale.US, "Яблоки: %.2fB", value));
        } else if (Data.apple >= 1000000) { // Миллион (M)
            double value = Data.apple / 1000000.0;
            appleLabel.setText(String.format(Locale.US, "Яблоки: %.2fM", value));
        } else if (Data.apple >= 1000) { // Тысяча (K)
            double value = Data.apple / 1000.0;
            appleLabel.setText(String.format(Locale.US, "Яблоки: %.2fK", value));
        } else {
            appleLabel.setText("Яблоки: " + Data.apple);
        }
    }

}
