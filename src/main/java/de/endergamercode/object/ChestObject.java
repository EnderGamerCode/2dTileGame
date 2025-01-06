package de.endergamercode.object;

import de.endergamercode.base.GamePanel;
import de.endergamercode.base.Sound;
import de.endergamercode.ui.Container;
import de.endergamercode.ui.ContainerType;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ChestObject extends Object {

    ArrayList<Object> contents;
    public Container container;
    GamePanel gamePanel;

    public ChestObject(GamePanel gamePanel, ArrayList<Object> contents) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        canBeDrawn=true;
        name = "chest";
        description = "A simple chest";
        collision = true;
        setContents(contents);
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        container = new Container("Chest", 6, 8, ContainerType.CHEST, gamePanel.tileSize * 2, contents, gamePanel);
    }

    public void addObject(Object object) {
        contents.add(object);
        gamePanel.ui.chat.addMessage("[+1] "+object.name);
    }

    public void transferObjectTo(Object object,int amount, ArrayList<Object> targetList) {
        contents.remove(object);
        targetList.add(object);
        gamePanel.playSoundEffect(Sound.ITEM_PICKUP);
        gamePanel.ui.chat.addMessage("[+1] "+object.name);
    }

    public void removeObject(Object object) {
        contents.remove(object);
        gamePanel.ui.chat.addMessage("[-1] "+object.name);
    }

    public ArrayList<Object> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Object> contents) {
        this.contents = contents;
    }

    public Container getContainer() {
        return container;
    }
}
