package de.endergamercode.entity;

import de.endergamercode.base.GamePanel;
import de.endergamercode.base.Sound;
import de.endergamercode.object.Object;
import de.endergamercode.ui.Container;
import de.endergamercode.ui.ContainerType;

import java.util.ArrayList;

public class Inventory {

    ArrayList<Object> items = new ArrayList<>();
    GamePanel gamePanel;
    Container inventoryContainer;

    public Inventory(Player player) {
        this.gamePanel = player.gamePanel;
        inventoryContainer = new Container("Inventory", 6, 11, ContainerType.INVENTORY, gamePanel.tileSize/2, gamePanel.tileSize * 2, items, gamePanel);
    }

    public void add(Object object){
        items.add(object);
        gamePanel.playSoundEffect(Sound.ITEM_PICKUP);
        gamePanel.ui.chat.addMessage("[+1] "+object.name);
    }

    public void transferObjectTo(Object object, ArrayList<Object> targetList) {
        items.remove(object);
        targetList.add(object);
        gamePanel.playSoundEffect(Sound.ITEM_PICKUP);
        gamePanel.ui.chat.addMessage("[+1] "+object.name);
    }

    public ArrayList<Object> getItemList() {
        return items;
    }

    public Container getInventoryContainer() {
        return inventoryContainer;
    }
}
