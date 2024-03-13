package objects;

import main.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Collectables extends Objects {
    public int health;
    public int initialHealth;
    public int id;
    public static final java.util.List<JProgressBar> healthBars = new ArrayList<>();
    public static final java.util.List<Timer> timers = new ArrayList<>();

    public JProgressBar healthBar;
    public JLabel label;
    public Timer timer;


    public void draw(Graphics2D g2, Panel panel, int index) {
        int screenX = worldX - panel.player.worldX + panel.player.screenX;
        int screenY = worldY - panel.player.worldY + panel.player.screenY;


        if (panel.coll[index] != null) {
            if (clickReceiver != null) clickReceiver.setBounds(screenX, screenY, solidArea.width, solidArea.height);
            if (healthBar != null)
                healthBar.setBounds(screenX, screenY - solidArea.height / 3, solidArea.width, solidArea.height / 3);
            if (label != null) label.setBounds(screenX, screenY + 23, solidArea.width, solidArea.height);


            if (worldX + panel.TILE_SIZE * 2 > panel.player.worldX - panel.player.screenX && worldX - panel.TILE_SIZE * 2 < panel.player.worldX + panel.player.screenX && worldY + panel.TILE_SIZE * 2 > panel.player.worldY - panel.player.screenY && worldY - panel.TILE_SIZE * 2 < panel.player.worldY + panel.player.screenY) {
                g2.drawImage(image, screenX, screenY, solidArea.width, solidArea.height, null);
                //g2.drawRect(screenX, screenY , solidArea.width, solidArea.height);
                if (clickReceiver != null) panel.add(clickReceiver);
                //if (label != null) panel.layeredPane.add(label, JLayeredPane.PALETTE_LAYER);
            }
        }
    }
    public void removeColl(Panel panel) {
        timer.stop();
        timer = null;
        healthBar.setVisible(false);
        panel.layeredPane.remove(healthBar);
        panel.layeredPane.remove(label);
        clickReceiver.removeAll();
        panel.remove(clickReceiver);
        panel.coll[id] = null;
        healthBars.remove(healthBar);
        timers.remove(timer);
    }
}
