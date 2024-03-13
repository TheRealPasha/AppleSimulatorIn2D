package main;

import entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryButton extends JButton implements PhaseInterface {
    Panel panel;
    InventoryPanel inventoryPanel;
    boolean isPhasing = false;
    boolean isPhased = false;
    public InventoryButton(Panel panel, InventoryPanel inventoryPanel)  {
        this.panel = panel;
        this.inventoryPanel = inventoryPanel;
        BufferedImage bf1 = null;
        try {
            bf1 = ImageIO.read(getClass().getResourceAsStream("/icons/InventoryIcon.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ImageIcon inventoryIcon = new ImageIcon(bf1);
        int width = inventoryIcon.getIconWidth();
        int height = inventoryIcon.getIconHeight();
        int x = panel.SCREEN_WIDTH - width - 10;
        int y = (panel.SCREEN_HEIGHT - height) / 2;
        //this.setBounds(x, y, width, height);
        //this.setBackground(Color.cyan);

        //URL url = getClass().getResource("/icons/InventoryIcon.png");

        this.setBounds(x,y,width,height);
        this.setIcon(inventoryIcon);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.addActionListener(l -> { // Показ инвентаря/Скрытие
            //System.out.println("QQqwfqfqf22");
            if (!isPhasing && !isPhased) {
                inventoryPanel.setVisible(true);
                panel.playSound(3);
                phaseIn();
                Player.allowMove = false;
            }
            else if (!isPhasing && isPhased) {
                panel.playSound(4);
                phaseOut();
                Player.allowMove = true;
            }
        });

    }

    @Override
    public void phaseIn() {
        int x = inventoryPanel.getX();
        int y = inventoryPanel.getY();
        if (!isPhasing) {
            int targetY = (panel.SCREEN_HEIGHT - inventoryPanel.getHeight()) / 2;
            Thread t = new Thread(() -> {
                isPhasing = true;
                for (int i = y; i >= targetY; i -= 80) {
                    if (i <= targetY+80) i = targetY;
                    inventoryPanel.setLocation(x, i);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                isPhasing = false;
                isPhased = true;
            });
            if (!t.isAlive()) {
                t.start();
            }
        }
    }

    @Override
    public void phaseOut() {
        int x = inventoryPanel.getX();
        int y = inventoryPanel.getY();
        if (!isPhasing) {
            int targetY = panel.SCREEN_HEIGHT + panel.SCREEN_HEIGHT / 60;
            Thread t = new Thread(() -> {
                isPhasing = true;
                for (int i = y; i <= targetY; i += 80) {
                    if (i >= targetY-80) i = targetY;
                    inventoryPanel.setLocation(x, i);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                isPhasing = false;
                isPhased = false;
                inventoryPanel.setVisible(false);
            });
            if (!t.isAlive()) {
                t.start();
            }
        }
    }
}
