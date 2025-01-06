package de.endergamercode.object;

import de.endergamercode.base.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object {

    GamePanel gamePanel;
    public int width, height;
    public Object(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        width = gamePanel.tileSize;
        height= gamePanel.tileSize;
    }

    public BufferedImage image;
    public String name;
    public String description;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,96,96);
    public int solidAreaDefaultX = 0,solidAreaDefaultY = 0;
    public boolean canBeDrawn = false;


    public void draw(Graphics2D graphics2D, GamePanel gamePanel){
        if(!canBeDrawn) return;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        if (worldX + gamePanel.tileSize> gamePanel.player.worldX-gamePanel.player.screenX &&
                worldX - gamePanel.tileSize< gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize> gamePanel.player.worldY-gamePanel.player.screenY &&
                worldY - gamePanel.tileSize< gamePanel.player.worldY + gamePanel.player.screenY ) {
            graphics2D.drawImage(image, screenX, screenY,width, height, null);
        }
    }
}
