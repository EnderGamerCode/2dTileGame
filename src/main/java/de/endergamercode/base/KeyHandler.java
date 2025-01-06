package de.endergamercode.base;

import de.endergamercode.gamestate.GameState;
import de.endergamercode.gamestate.GameStateManager;
import de.endergamercode.ui.Container;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, rightReleased, leftReleased;
    private String lastPressed = "D";
    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (gamePanel.gameStateManager.getCurrentGameState().equals(GameState.PLAYING)) {
            playingState(keyCode);
        } else if (gamePanel.gameStateManager.getCurrentGameState().equals(GameState.PAUSED)) {
            pausedState(keyCode);
        } else if (gamePanel.gameStateManager.getCurrentGameState().equals(GameState.INVENTORY)) {
            inventoryState(keyCode);
        } else if (gamePanel.gameStateManager.getCurrentGameState().equals(GameState.CHEST)) {
            chestState(keyCode);
        }
    }

    void playingState(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> {
                leftPressed = true;
                lastPressed = "A";
                unRelease();
            }
            case KeyEvent.VK_D -> {
                rightPressed = true;
                lastPressed = "D";
                unRelease();
            }
            case KeyEvent.VK_ESCAPE, KeyEvent.VK_P -> {
                gamePanel.gameStateManager.setCurrentGameState(GameState.PAUSED);
                gamePanel.pauseMusic();
            }
            case KeyEvent.VK_E -> {
                gamePanel.gameStateManager.setCurrentGameState(GameState.INVENTORY);
                gamePanel.setMusicVolume(-8);
                gamePanel.playSoundEffect(Sound.INVENTORY_OPEN);
            }

        }
    }


    void pausedState(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE, KeyEvent.VK_P, KeyEvent.VK_E -> {
                gamePanel.gameStateManager.setCurrentGameState(GameState.PLAYING);
                gamePanel.resumeMusic();
            }
        }
    }

    void inventoryState(int keyCode) {
        Container inventoryContainer = gamePanel.player.inventory.getInventoryContainer();
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE, KeyEvent.VK_E -> {
                gamePanel.gameStateManager.setCurrentGameState(GameState.PLAYING);
                gamePanel.setMusicVolume(0);
            }
            case KeyEvent.VK_W -> handleMovementInventory(-1, 0, inventoryContainer.slotRow > 0);
            case KeyEvent.VK_A -> handleMovementInventory(0, -1, inventoryContainer.slotCol > 0);
            case KeyEvent.VK_S -> handleMovementInventory(1, 0, inventoryContainer.slotRow < inventoryContainer.maxRows - 1);
            case KeyEvent.VK_D -> handleMovementInventory(0, 1, inventoryContainer.slotCol < inventoryContainer.maxColumns - 1);
        }
    }
    void chestState(int keyCode) {
        Container container = gamePanel.ui.currentChest.container;
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE, KeyEvent.VK_E -> {
                gamePanel.gameStateManager.setCurrentGameState(GameState.PLAYING);
                gamePanel.playSoundEffect(Sound.CHEST_CLOSE);
            }
            case KeyEvent.VK_W -> handleMovement(-1, 0, container.slotRow > 0);
            case KeyEvent.VK_A -> handleMovement(0, -1, container.slotCol > 0);
            case KeyEvent.VK_S -> handleMovement(1, 0, container.slotRow < container.maxRows - 1);
            case KeyEvent.VK_D -> handleMovement(0, 1, container.slotCol < container.maxColumns - 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_S -> {
                if (keyCode == KeyEvent.VK_W) upPressed = false;
                if (keyCode == KeyEvent.VK_S) downPressed = false;
                setLastReleased();
            }
            case KeyEvent.VK_A -> {
                leftPressed = false;
                leftReleased = true;
            }
            case KeyEvent.VK_D -> {
                rightPressed = false;
                rightReleased = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    private void unRelease() {
        rightReleased = false;
        leftReleased = false;
    }

    public String getLastPressed() {
        return lastPressed;
    }

    private void setLastReleased() {
        if (getLastPressed().equals("A")) {
            leftReleased = true;
        } else if (getLastPressed().equals("D")) {
            rightReleased = true;
        }
    }

    private void handleMovementInventory(int rowDelta, int colDelta, boolean condition) {
        if (condition) {
            gamePanel.player.inventory.getInventoryContainer().moveCursor(rowDelta, colDelta);
            gamePanel.playSoundEffect(Sound.CONTAINER_MOVE);
        }
    }

    private void handleMovement(int rowDelta, int colDelta, boolean condition) {
        if (condition) {
            gamePanel.ui.currentChest.container.moveCursor(rowDelta, colDelta);
            gamePanel.playSoundEffect(Sound.CONTAINER_MOVE);
        }
    }


}
