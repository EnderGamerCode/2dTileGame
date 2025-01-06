package de.endergamercode.base;

import de.endergamercode.gamestate.GameState;
import de.endergamercode.object.ChestObject;
import de.endergamercode.object.Object;
import de.endergamercode.ui.Container;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class ClickHandler implements MouseListener {

    GamePanel gamePanel;

    public ClickHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Convert screen coordinates to world coordinates
        int mouseWorldX, mouseWorldY;
        if (gamePanel.player.customCamera) {
            mouseWorldX = mouseX;
            mouseWorldY = mouseY;
        } else {
            mouseWorldX = mouseX + gamePanel.player.worldX - gamePanel.player.screenX;
            mouseWorldY = mouseY + gamePanel.player.worldY - gamePanel.player.screenY;
        }

        // Convert world coordinates to tile indices
        int tileX = mouseWorldX / gamePanel.tileSize;
        int tileY = mouseWorldY / gamePanel.tileSize;
        System.out.println(String.format("Tile clicked: (%02d|%02d)", tileX, tileY));


        if (gamePanel.gameStateManager.getCurrentGameState().equals(GameState.PLAYING)) {
            for (Object object : gamePanel.objects) {
                //check if clicked on chest
                if (object != null) {
                    if (mouseWorldX >= object.worldX && mouseWorldX <= object.worldX + object.solidArea.x + object.solidArea.getWidth()
                            && mouseWorldY >= object.worldY && mouseWorldY <= object.worldY + object.solidArea.y + object.solidArea.getHeight()) {
                        if (object.name.equals("chest")) {
                            gamePanel.ui.openChest((ChestObject) object);
                            gamePanel.playSoundEffect(Sound.CHEST_OPEN);
                        }
                    }
                }
            }
        } else if (gamePanel.gameStateManager.getCurrentGameState().equals(GameState.CHEST)) {

            ChestObject chestObject = gamePanel.ui.currentChest;
            Container chestContainer = gamePanel.ui.currentChest.container;
            if (mouseX > chestContainer.containerX + 20 && mouseY > chestContainer.containerY + 20 &&
                    mouseX < chestContainer.containerX + chestContainer.containerWidth - 20 && mouseY < chestContainer.containerY + chestContainer.containerHeight - 20) {
                gamePanel.playSoundEffect(Sound.CONTAINER_SELECT);
                //GET INDEX OF CLICK
                int mouseXinSelection = mouseX - (chestContainer.containerX + 20);
                int mouseYinSelection = mouseY - (chestContainer.containerY + 20);

                int col = mouseXinSelection / gamePanel.tileSize;
                int row = mouseYinSelection / gamePanel.tileSize;

                int index = col + (row * chestContainer.maxColumns);

                if (!chestObject.getContents().isEmpty()) {
                    System.out.println(index);
                    if(chestObject.getContents() == null) return;
                    Object indexObject = chestObject.getContents().get(index);

                    System.out.println("Index: " + index + " Object : " + indexObject.name);

                    chestObject.transferObjectTo(indexObject,1,gamePanel.player.inventory.getItemList());

                }

            }


        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
