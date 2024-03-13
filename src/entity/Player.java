package entity;

import main.KeyHandler;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    Panel panel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public static boolean allowMove = true;

    public Player(Panel panel, KeyHandler keyHandler) {
       this.panel = panel;
       this.keyHandler = keyHandler;

       screenX = panel.SCREEN_WIDTH/2 - panel.TILE_SIZE;
       screenY = panel.SCREEN_HEIGHT/2 - panel.TILE_SIZE;


       solidArea = new Rectangle(screenX + panel.TILE_SIZE/2, screenY+ panel.TILE_SIZE, panel.TILE_SIZE, panel.TILE_SIZE);
       solidAreaDefaultX = solidArea.x;
       solidAreaDefaultY = solidArea.y;

       setDefault();
       getPlayerImage();
    }
    public void setDefault() {
        worldX = 1300;
        worldY = 1300;
        speed = 5;
        direction = "down";
    }
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update() {
        if (keyHandler.upHold || keyHandler.downHold || keyHandler.leftHold || keyHandler.rightHold) {
            spriteCounter++;
            if (spriteCounter > 15) { // Каждые 15 кадров меняется спрайт
                if (spriteNum == 1) {
                    spriteNum = 2;
                   //panel.playSound(9);
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                    //panel.playSound(10);
                }
                spriteCounter = 0;
            }
        }
        collisionOn = false;
        panel.collisionHandler.checkTile(this);
        int objIndex = panel.collisionHandler.checkObject(this, true);

        int dx = 0;
        int dy = 0;
            if (keyHandler.upHold) {
                direction = "up";
                if (!panel.collisionHandler.upMove) dy -= speed;
            } else if (keyHandler.downHold) {
                direction = "down";
                if (!panel.collisionHandler.downMove) dy += speed;
            }
            if (keyHandler.leftHold) {
                direction = "left";
                if (!panel.collisionHandler.leftMove) dx -= speed;
            } else if (keyHandler.rightHold) {
                direction = "right";
                if (!panel.collisionHandler.rightMove) dx += speed;
            }
            if (!collisionOn && allowMove) {
                worldX += dx;
                worldY += dy;
                hbWorldX = worldX + panel.TILE_SIZE / 2;
                hbWorldY = worldY + panel.TILE_SIZE;
                //System.out.println(worldX + " " + hbWorldX+ "  " + worldY + " " + hbWorldY);
            }
        if (worldY <= 0) {
            worldY += 200;
            hbWorldX = worldX + panel.TILE_SIZE / 2;
        }
        if (worldX <= 0) {
            worldX += 200;
            hbWorldY = worldY + panel.TILE_SIZE;
        }
        if (worldY >= panel.SCREEN_HEIGHT * 5.3) {
            worldY -= 200;
            hbWorldX = worldX + panel.TILE_SIZE / 2;
        }
        if (worldX >= panel.SCREEN_WIDTH * 3) {
            worldX -= 200;
            hbWorldY = worldY + panel.TILE_SIZE;
       }
        //System.out.println(hbWorldX);
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) image = up1;
                if (spriteNum == 2) image = up2;
            }
            case "down" -> {
                if (spriteNum == 1) image = down1;
                if (spriteNum == 2) image = down2;
            }
            case "left" -> {
                if (spriteNum == 1) image = left1;
                if (spriteNum == 2) image = left2;
            }
            case "right" -> {
                if (spriteNum == 1) image = right1;
                if (spriteNum == 2) image = right2;
            }
        }
        g2.drawImage(image, screenX, screenY, panel.TILE_SIZE*2, panel.TILE_SIZE*2, null);
        //g2.setColor(Color.white);
        //g2.drawRect(solidArea.x, solidArea.y , solidArea.width, solidArea.height);
    }
}
