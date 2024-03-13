package objects;

import data.Data;
import main.Panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.util.Timer;

public class ColApple extends Collectables {
    //private Color[] healthColors = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
    Panel panel;


    public ColApple(Panel panel, int rowMult, int colMult, int widthMult, int heightMult, int id) {
        this.panel = panel;
        name = "Apple";
        mapOn = "map0.txt";
        this.id = id;
        health = random.nextInt(491) + 10;
        initialHealth = health;
        //health = 20;

        worldX = rowMult * panel.TILE_SIZE;
        worldY = colMult * panel.TILE_SIZE;
        solidArea.width *= widthMult;
        solidArea.height *= heightMult;
        //int screenX = worldX - panel.player.worldX + panel.player.screenX;
        //int screenY = worldY - panel.player.worldY + panel.player.screenY;
        clickReceiver = new JButton();
        clickReceiver.setBorderPainted(false);
        clickReceiver.setContentAreaFilled(false);
        clickReceiver.setFocusPainted(false);
        clickReceiver.setOpaque(false);
        clickReceiver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label = new JLabel(String.valueOf(this.health));
        //label.setBounds(solidArea.x, solidArea.y, solidArea.width, solidArea.height);
        label.setFont(new Font("Arial", Font.BOLD, 50));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        panel.layeredPane.add(label, JLayeredPane.PALETTE_LAYER);
        label.setVisible(false);

        healthBar = new JProgressBar(0, initialHealth);
        healthBar.setValue(health);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.GREEN);
        healthBar.setBorderPainted(false);
        healthBar.setFont(new Font("Arial",Font.BOLD, 23));
        healthBar.setString("Çהמנמגüו: " + health + "/" + initialHealth);
        panel.layeredPane.add(healthBar, JLayeredPane.PALETTE_LAYER);
        healthBar.setVisible(false);

        timer = new Timer(1000, l -> {
            if (health > 0) {
                if (health - Data.damage > 0) panel.playSound(1);
                health -= Data.damage;
                updateHealth();
            }
            if (health <= 0) {
                //System.out.println("qfqfqfqfq1@!!!!");
                // timer.stop();
                panel.playSound(2);
                Data.apple += (initialHealth / random.nextDouble(0.8, 1.3));
                Data.updateApple();
                System.out.println(Data.apple);
                removeColl(panel);
            }
        });

        clickReceiver.addMouseListener(new MouseAdapter() {
            //boolean timerRunning = false;
            @Override
            public void mouseReleased(MouseEvent evt) {
                if (timer.isRunning()) {
                    for (JProgressBar bar : healthBars) {
                        bar.setVisible(false);
                    }
                    healthBars.clear();
                    for (Timer timer : timers) {
                        timer.stop();
                    }
                    timers.clear();
                    timer.stop();
                }
                else {
                    for (JProgressBar bar : healthBars) {
                        bar.setVisible(false);
                    }
                    healthBars.clear();
                    for (Timer timer : timers) {
                        timer.stop();
                    }
                    timers.clear();
                    healthBars.add(healthBar);
                    healthBar.setVisible(true);
                    timer.start();
                    if (health <= 0) timer.stop();
                    timers.add(timer);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setVisible(false);
            }
        });
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/apple.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateHealth() {
        if (healthBar != null && health > 0) {
            this.healthBar.setValue(health);
            this.healthBar.setString("Çהמנמגüו: " + health + "/" + initialHealth);
        }
    }
}
