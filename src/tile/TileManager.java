package tile;

import main.Panel;
import objects.Collectables;
import objects.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    Panel panel;
    public Tile[] tile;
    private String currentMap;
    public int[][] mapTileNum;

    public TileManager(Panel panel) {
        this.panel = panel;

        tile = new Tile[10]; // na poka
        mapTileNum = new int[panel.maxWorldCol][panel.maxWorldRow];

        getTileImage();
        loadMap("map0.txt");
    }
    public void loadMap(String mapFileName) {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/"+mapFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            String line;
            while ((line = br.readLine()) != null && row < panel.maxWorldRow) {
                String[] numbers = line.split(" ");

                for (int col = 0; col < panel.maxWorldCol && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
                row++;
            }
            br.close();
            currentMap = mapFileName;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void teleportPlayerToMap(String mapFileName, int playerX, int playerY) {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/" + mapFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            String line;
            while ((line = br.readLine()) != null && row < panel.maxWorldRow) {
                String[] numbers = line.split(" ");

                for (int col = 0; col < panel.maxWorldCol && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
                row++;
            }
            br.close();
            panel.player.worldX = playerX;
            panel.player.worldY = playerY;
            currentMap = mapFileName;
            for (Collectables apple : panel.coll) {
                if (apple != null) apple.removeColl(panel);
            }
            for (Objects obj : panel.obj) {
                if (obj != null) obj.removeObj(obj);
            }
           panel.assetSetter.spawnEggs();

          // panel.assetSetter.spawnEggs();

            //panel.requestFocus();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getTileImage() {
        try {
            // Трава
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/0grass.png"));
            // Трава с коллизией (не знаю зачем пока)
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/1grasscollision.png"));
            tile[1].collision = true;
            // Вода
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/2water.png"));
            tile[2].collision = true;
            // Ничего
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/3notile.png"));
            //tile[3].collision = true;
            // Земля
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/4dirt.png"));
            // Земля темнее
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/5dirt2.png"));
            // Камень
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/6rock.png"));
            tile[6].collision = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        for (int row = 0; row < panel.maxWorldRow; row++) {
            for (int col = 0; col < panel.maxWorldCol; col++) {
                int tileNum = mapTileNum[col][row];
                int x = col * panel.TILE_SIZE;
                int y = row * panel.TILE_SIZE;
                int screenX = x - panel.player.worldX + panel.player.screenX;
                int screenY = y - panel.player.worldY + panel.player.screenY;

                if (x + panel.TILE_SIZE*2 > panel.player.worldX - panel.player.screenX && x - panel.TILE_SIZE*2 < panel.player.worldX+panel.player.screenX&&y + panel.TILE_SIZE*2 > panel.player.worldY - panel.player.screenY && y - panel.TILE_SIZE*2 <panel.player.worldY+panel.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, panel.TILE_SIZE, panel.TILE_SIZE, null);
                }
               // g2.drawImage(tile[tileNum].image, screenX, screenY, panel.TILE_SIZE, panel.TILE_SIZE, null);
            }
        }
    }
    public String getCurrentMap() {
        return currentMap;
    }
}
