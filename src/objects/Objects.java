package objects;

import main.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import static main.Panel.panel;

public class Objects {
    public BufferedImage image;
    public String name;
    public String mapOn;
    public int index;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,120,120);
    public JButton clickReceiver;
    public Random random = new Random();

    public void draw(Graphics2D g2, Panel panel, int index) {
            int screenX = worldX - panel.player.worldX + panel.player.screenX;
            int screenY = worldY - panel.player.worldY + panel.player.screenY;
            if (clickReceiver != null) clickReceiver.setBounds(screenX, screenY, solidArea.width, solidArea.height);

            if (worldX + panel.TILE_SIZE * 2 > panel.player.worldX - panel.player.screenX && worldX - panel.TILE_SIZE * 2 < panel.player.worldX + panel.player.screenX && worldY + panel.TILE_SIZE * 2 > panel.player.worldY - panel.player.screenY && worldY - panel.TILE_SIZE * 2 < panel.player.worldY + panel.player.screenY) {
                g2.drawImage(image, screenX, screenY, solidArea.width, solidArea.height, null);
                //g2.drawRect(screenX, screenY , solidArea.width, solidArea.height);
                if (clickReceiver != null) panel.add(clickReceiver);
                //if (label != null) panel.layeredPane.add(label, JLayeredPane.PALETTE_LAYER);\
                //System.out.println(Arrays.toString(panel.getComponents()));
        }
    }
    public void removeObj(Objects obj) {
        for (int i = 0; i < panel.obj.length; i++) {
            if (panel.obj[i] == obj) {
                panel.remove(clickReceiver);
                panel.obj[i] = null;
                break;
            }
        }
    }
}
