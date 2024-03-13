package main;

import entity.Entity;

public class CollisionHandler {
    Panel panel;
    public boolean upMove = false;
    public boolean downMove = false;
    public boolean leftMove = false;
    public boolean rightMove = false;
    public CollisionHandler(Panel panel) {
        this.panel = panel;
    }
    public void checkTile(Entity entity) {
        int eLeftWorldX = entity.hbWorldX;
        int eRightWorldX = entity.hbWorldX + entity.solidArea.width;
        int eTopWorldY = entity.hbWorldY;
        int eBottomWorldY = entity.hbWorldY + entity.solidArea.height;

        int eLeftCol = eLeftWorldX / panel.TILE_SIZE;
        int eRightCol = eRightWorldX / panel.TILE_SIZE;
        int eTopRow = eTopWorldY / panel.TILE_SIZE;
        int eBottomRow = eBottomWorldY / panel.TILE_SIZE;

        int tileNum1, tileNum2;
        if (entity.direction.equals("up")) {
            eTopRow = (eTopWorldY - entity.speed) / panel.TILE_SIZE;
            tileNum1 = panel.tileManager.mapTileNum[eLeftCol][eTopRow];
            tileNum2 = panel.tileManager.mapTileNum[eRightCol][eTopRow];
            if (panel.tileManager.tile[tileNum1].collision || panel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
                upMove = true;
                //System.out.println("up collision");
            }
            else upMove = false;
        }
        if (entity.direction.equals("down")) {
            eBottomRow = (eBottomWorldY + entity.speed) / panel.TILE_SIZE;
            tileNum1 = panel.tileManager.mapTileNum[eLeftCol][eBottomRow];
            tileNum2 = panel.tileManager.mapTileNum[eRightCol][eBottomRow];
            if (panel.tileManager.tile[tileNum1].collision || panel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
                downMove = true;
                //System.out.println("down collision");
            }
            else downMove = false;
        }
        if (entity.direction.equals("left")) {
            eLeftCol = (eLeftWorldX - entity.speed) / panel.TILE_SIZE;
            tileNum1 = panel.tileManager.mapTileNum[eLeftCol][eTopRow];
            tileNum2 = panel.tileManager.mapTileNum[eLeftCol][eBottomRow];
            if (panel.tileManager.tile[tileNum1].collision || panel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
                leftMove = true;
               // System.out.println("left collision");
            }
            else leftMove = false;
        }
        if (entity.direction.equals("right")) {
            eRightCol = (eRightWorldX + entity.speed) / panel.TILE_SIZE;
            tileNum1 = panel.tileManager.mapTileNum[eRightCol][eTopRow];
            tileNum2 = panel.tileManager.mapTileNum[eRightCol][eBottomRow];
            if (panel.tileManager.tile[tileNum1].collision || panel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
                rightMove = true;
                //System.out.println("right collision");
            }
            else rightMove = false;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < panel.obj.length; i++) {
            if (panel.obj[i] != null && panel.obj[i].solidArea != null) {
                panel.obj[i].solidArea.x = panel.obj[i].worldX;
                panel.obj[i].solidArea.y = panel.obj[i].worldY;

                entity.solidArea.x = entity.hbWorldX;
                entity.solidArea.y = entity.hbWorldY;

                switch (entity.direction) {
                    case "up": {
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(panel.obj[i].solidArea)) {
                            if (panel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    }
                    case "down": {
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(panel.obj[i].solidArea)) {
                            if (panel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    }
                    case "left": {
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(panel.obj[i].solidArea)) {
                            if (panel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    }
                    case "right": {
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(panel.obj[i].solidArea)) {
                            if (panel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
            }
        }

        return index;
    }
}
