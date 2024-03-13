package main;

import javax.swing.*;
import java.awt.*;

import static main.Panel.panel;

public class MapsPanel extends JPanel implements PhaseInterface {
    //Panel panel;
    boolean isPhasing = false;
    int arcWidth;
    public MapsPanel(Panel panel) {
        int width = panel.SCREEN_WIDTH / 2;
        int height = panel.SCREEN_HEIGHT / 2;
        int x = -panel.SCREEN_WIDTH / 2 + panel.SCREEN_WIDTH / 60;
        int y = panel.SCREEN_HEIGHT / 4;

        this.setBounds(x, y, width, height);
        this.setBackground(Color.decode("#FF726D"));
        this.addMouseListener(new MouseThing(this, panel, width, height, x, y));
        setRoundCorners(500);

        int numButtons = 3; // Количество кнопок (Количество карт)
        int buttonWidth = (width - (numButtons + 1) * 10) / numButtons;
        int buttonHeight = height / 3 + height / 4;
        int buttonY = height / 2 - buttonHeight / 2;
        int buttonX = 10;

        MapsButton button1 = new MapsButton("Карта 1", 100, 50);
        button1.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        button1.addActionListener(new MapButtonListener("map0.txt", 1000, 1000, panel));

        MapsButton button2 = new MapsButton("Карта 2", 100, 50);
        button2.setBounds(buttonX + buttonWidth + 10, buttonY, buttonWidth, buttonHeight);
        button2.addActionListener(new MapButtonListener("map1.txt", 1000, 1000, panel));

        MapsButton button3 = new MapsButton("Карта 3", 100, 50);
        button3.setBounds(buttonX + 2 * (buttonWidth + 10), buttonY, buttonWidth, buttonHeight);

        //MapsButton button4 = new MapsButton("button3", 100, 50);
        //button4.setBounds(buttonX + 3 * (buttonWidth + 10), buttonY, buttonWidth, buttonHeight);

        this.add(button1);
        this.add(button2);
        this.add(button3);
        //this.add(button4);
    }
    public void setRoundCorners(int arcWidth) {
        this.arcWidth = arcWidth;
        setOpaque(false);
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(0, arcWidth, 0, arcWidth));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackground());
        int arcHeight = arcWidth / 2 + 10;
        g.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        super.paintComponent(g);
    }

    @Override
    public void phaseIn() {
        int x = this.getX();
        int y = this.getY();
        if (!isPhasing) {
            Thread t = new Thread(() -> {
                isPhasing = true;
                for (int i = x; i <= 0; i += 30) {
                    if (i >= -30) {
                        i = 0;
                    }
                    // System.out.println(i);
                    this.setLocation(i, y);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        System.out.println("fqrq");
                        throw new RuntimeException(e);
                    }
                }
                isPhasing = false;
            });
            if (!t.isAlive()) {
                t.start();
            }
        }
    }
    @Override
    public void phaseOut() {
        int x = this.getX();
        int y = this.getY();
        if (!isPhasing) {
            Thread t = new Thread(() -> {
                isPhasing = true;
                int targetX = -panel.SCREEN_WIDTH / 2 + panel.SCREEN_WIDTH / 60;
                for (int i = x; i >= targetX; i -= 30) {
                    if (i <= targetX+30) {
                        i = targetX;
                    }
                    this.setLocation(i, y);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                isPhasing = false;
            });
            if (!t.isAlive()) {
                t.start();
            }
        }
    }
}
