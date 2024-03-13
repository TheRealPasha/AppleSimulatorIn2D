package main;

import objects.ColApple;
import objects.Collectables;
import objects.eggs.Egg1;

import javax.swing.*;
import java.util.Random;

public class AssetSetter {
    private Panel panel;
    private final Random random = new Random();
    public static final int APPLE_WIDTH_MULT = 2;
    public static final int APPLE_HEIGHT_MULT = 2;
    private Thread thread;
    public Timer timer;
   // private boolean bruh = false;

    public AssetSetter(Panel panel) {
        this.panel = panel;
       // thread = new AssetsThread(panel);
    }

    public void generateObjects() {
        timer = new Timer(500, l -> {
            //Thread.currentThread().setPriority(10);
            synchronized (panel.coll) {
                if (panel.tileManager.getCurrentMap().equals("map0.txt")) {
                    for (int z = 0; z < panel.coll.length; z++) {
                        if (panel.coll[z] == null) {
                            ColApple apple = generateApple(z);
                            panel.coll[z] = apple;
                            break;
                        }
                    }
                    //if (panel.coll[i] == null) {

                    //}
                }
            }
        });
        timer.start();

       //panel.obj[0] = new Egg1(panel);
    }

    public void spawnEggs() {
        for (int i = 0; i < panel.eggs.length; i++) {
            if (panel.eggs[i] != null) {
                if (panel.eggs[i].mapOn.equals(panel.tileManager.getCurrentMap())) {
                    panel.obj[i] = panel.eggs[i];
                }
            }
        }
    }

    private ColApple generateApple(int index) {
        int randRow, randCol;
        do {
            //randRow = random.nextInt(-6, 7) + panel.player.worldX/panel.TILE_SIZE;
            //randCol = random.nextInt(-6, 7) + panel.player.worldY/panel.TILE_SIZE; // Åñëè áóäó þçàòü, ÒÎ ÍÅ ÇÀÁÛÒÜ ÏÎÑÒÀÂÈÒÜ 10 ÎÁÚÅÊÒÎÂ Â ÏÀÍÅËÈ
            randRow = random.nextInt(3, 47);
            randCol = random.nextInt(3, 47);
        } while (isOccupied(randRow, randCol) || randRow == panel.eggs[0].worldX/ panel.TILE_SIZE || randCol == panel.eggs[0].worldY/ panel.TILE_SIZE);

        ColApple apple = new ColApple(panel, randRow, randCol, APPLE_WIDTH_MULT, APPLE_HEIGHT_MULT, index);
        apple.solidArea.x = apple.worldX;
        apple.solidArea.y = apple.worldY;
        return apple;
    }
    private boolean isOccupied(int row, int col) {
        for (Collectables obj : panel.coll) {
            if (obj != null && obj.solidArea.intersects(row * panel.TILE_SIZE, col * panel.TILE_SIZE, panel.TILE_SIZE, panel.TILE_SIZE)) {
                return true;
            }
        }
        return false;
    }
}
