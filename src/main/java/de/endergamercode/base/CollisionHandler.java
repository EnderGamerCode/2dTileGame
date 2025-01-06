package de.endergamercode.base;

import de.endergamercode.entity.Entity;
import de.endergamercode.object.Object;

public class CollisionHandler {

    GamePanel gamePanel;

    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        // Calculate edges of the entity in world coordinates
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Convert edges to grid-based tile indices
        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        // Determine target tiles based on movement
        int tileNum1 = 0, tileNum2 = 0;
        if (gamePanel.keyHandler.upPressed) {
            entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.map[entityTopRow][entityLeftCol];
            tileNum2 = gamePanel.tileManager.map[entityTopRow][entityRightCol];
        } else if (gamePanel.keyHandler.downPressed) {
            entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.map[entityBottomRow][entityLeftCol];
            tileNum2 = gamePanel.tileManager.map[entityBottomRow][entityRightCol];
        } else if (gamePanel.keyHandler.leftPressed) {
            entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.map[entityTopRow][entityLeftCol];
            tileNum2 = gamePanel.tileManager.map[entityBottomRow][entityLeftCol];
        } else if (gamePanel.keyHandler.rightPressed) {
            entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.map[entityTopRow][entityRightCol];
            tileNum2 = gamePanel.tileManager.map[entityBottomRow][entityRightCol];
        }

        // Check for collisions
        if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
            entity.colliding = true;
        }
    }

    public int checkObject(Entity entity, boolean player){
        int index = -1;

        for(int i = 0; i < gamePanel.objects.length ; i++){
            Object object = gamePanel.objects[i];
            if(object != null){
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                object.solidArea.x = object.worldX + object.solidArea.x;
                object.solidArea.y = object.worldY + object.solidArea.y;

                KeyHandler keyHandler = gamePanel.keyHandler;
                if(keyHandler.upPressed){
                    entity.solidArea.y -= entity.speed;
                    if(entity.solidArea.intersects(object.solidArea)){
                        if(object.collision){
                            entity.colliding = true;
                        }
                        index=i;
                    }
                }else if(keyHandler.downPressed){
                    entity.solidArea.y += entity.speed;
                    if(entity.solidArea.intersects(object.solidArea)){
                        if(object.collision){
                            entity.colliding = true;
                        }
                        index=i;
                    }
                }else if(keyHandler.leftPressed){
                    entity.solidArea.x -= entity.speed;
                    if(entity.solidArea.intersects(object.solidArea)){
                        if(object.collision){
                            entity.colliding = true;
                        }
                        index=i;
                    }
                }else if(keyHandler.rightPressed){
                    entity.solidArea.y += entity.speed;
                    if(entity.solidArea.intersects(object.solidArea)){
                        if(object.collision){
                            entity.colliding = true;
                        }
                        index=i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                object.solidArea.x = object.solidAreaDefaultX;
                object.solidArea.y = object.solidAreaDefaultY;



            }
        }

        return index;
    }

}
