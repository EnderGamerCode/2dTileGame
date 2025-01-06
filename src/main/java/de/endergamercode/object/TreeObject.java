package de.endergamercode.object;

import de.endergamercode.base.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TreeObject extends Object{

    public TreeObject(GamePanel gamePanel) {
        super(gamePanel);
        canBeDrawn = true;
        name = "tree";
        description = "tree";
        width = (gamePanel.tileSize*2);
        height = (gamePanel.tileSize*2);
        solidAreaDefaultX = width/4;
        solidAreaDefaultY = (int) (height-height/1.3);
        solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, width/2, (int) (height/1.3));

        collision = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/tree1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        super.draw(graphics2D, gamePanel);
        graphics2D.setColor(Color.RED);
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        if (worldX + gamePanel.tileSize> gamePanel.player.worldX-gamePanel.player.screenX &&
                worldX - gamePanel.tileSize< gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize> gamePanel.player.worldY-gamePanel.player.screenY &&
                worldY - gamePanel.tileSize< gamePanel.player.worldY + gamePanel.player.screenY ) {
            graphics2D.drawRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);
        }
    }
}
