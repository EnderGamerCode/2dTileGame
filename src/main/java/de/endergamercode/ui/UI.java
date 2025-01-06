package de.endergamercode.ui;

import de.endergamercode.base.GamePanel;
import de.endergamercode.gamestate.GameState;
import de.endergamercode.object.ChestObject;

import java.awt.*;

public class UI {

    Graphics2D graphics2D;
    GamePanel gamePanel;
    Font bigFont;
    Font smallFont;
    public ChestObject currentChest;
    DrawUtils drawUtils;
    public Chat chat;


    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        drawUtils = new DrawUtils(gamePanel);
        bigFont = new Font("Constantia", Font.PLAIN, 40);
        chat = new Chat(gamePanel, drawUtils);
        smallFont = new Font("Ink Free", Font.BOLD, 40);
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
        graphics2D.setFont(bigFont);
        graphics2D.setColor(Color.WHITE);

        GameState currentState = gamePanel.gameStateManager.getCurrentGameState();
        switch (currentState) {
            case PLAYING -> {
            } // Main game screen
            case PAUSED -> drawPauseScreen();
            case INVENTORY -> drawContainer(gamePanel.player.inventory.getInventoryContainer());

            case CHEST -> drawContainer(currentChest.container);
        }
        chat.draw(graphics2D);
    }

    public void drawContainer(Container container) {
        if (container != null) {
            container.graphics2D = graphics2D;
            container.draw();
        }
    }

    public void openChest(ChestObject object) {
        gamePanel.ui.setChest((ChestObject) object);
        gamePanel.gameStateManager.setCurrentGameState(GameState.CHEST);
    }

    public void drawPauseScreen() {
        drawUtils.drawTitle("GAME PAUSED", Color.WHITE, graphics2D.getFont().deriveFont(Font.PLAIN, 200F), graphics2D);
    }

    public int getCenteredTextX(String text) {
        int stringLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.screenWidth / 2 - stringLength / 2;
    }

    public void setChest(ChestObject object) {
        this.currentChest = object;
    }


}
