package de.endergamercode.base;

import de.endergamercode.object.*;
import de.endergamercode.object.Object;

import java.util.ArrayList;

public class AssetManager {

    GamePanel gamePanel;

    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject(){
        gamePanel.objects[0] = new KeyObject(gamePanel);
        gamePanel.objects[0].worldX = 11 * gamePanel.tileSize;
        gamePanel.objects[0].worldY =  4 * gamePanel.tileSize;

        gamePanel.objects[1] = new KeyObject(gamePanel);
        gamePanel.objects[1].worldX = 16* gamePanel.tileSize;
        gamePanel.objects[1].worldY = 10* gamePanel.tileSize;



        ArrayList<Object> objects = new ArrayList<>();
        objects.add(new BreadObject(gamePanel));
        objects.add(new KeyObject(gamePanel));
        objects.add(new BreadObject(gamePanel));
        objects.add(new BreadObject(gamePanel));
        objects.add(new KeyObject(gamePanel));
        objects.add(new BreadObject(gamePanel));
        objects.add(new KeyObject(gamePanel));
        objects.add(new KeyObject(gamePanel));
        gamePanel.objects[2] = new ChestObject(gamePanel,objects);
        gamePanel.objects[2].worldX = 12* gamePanel.tileSize;
        gamePanel.objects[2].worldY = 29* gamePanel.tileSize;

        /*gamePanel.objects[3] = new TreeObject(gamePanel);
        gamePanel.objects[3].worldX = 20* gamePanel.tileSize;
        gamePanel.objects[3].worldY = 15* gamePanel.tileSize;*/
    }

}
