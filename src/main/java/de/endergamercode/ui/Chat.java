package de.endergamercode.ui;

import de.endergamercode.base.GamePanel;

import java.awt.*;
import java.util.ArrayList;


public class Chat {

    DrawUtils drawUtils;
    GamePanel gamePanel;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounters = new ArrayList<>();



    public Chat(GamePanel gamePanel, DrawUtils drawUtils) {
        this.gamePanel = gamePanel;
        this.drawUtils = drawUtils;
    }

    public void draw(Graphics2D graphics2D) {
        //drawBackground(graphics2D);
        drawMessages(graphics2D);
    }

    private void drawBackground(Graphics2D graphics2D) {
        int drawX = (int) (gamePanel.screenWidth-gamePanel.tileSize*3.7);
        int drawY = (int) (gamePanel.tileSize*7.5);
        int drawWidth = (int) (gamePanel.tileSize*3.7);
        int drawHeight = (int) (gamePanel.tileSize*2.48);
        drawUtils.drawSubWindow(graphics2D,drawX,drawY, drawWidth, drawHeight);
    }

    public void drawMessages(Graphics2D graphics2D) {
        int drawY = gamePanel.tileSize * 8;

        graphics2D.setFont(gamePanel.ui.smallFont);

        for (int i = 0; i < messages.size(); i++) { // Iterate in reverse
            String message = messages.get(i);

            if (messageCounters.get(i) > 0) {
                drawUtils.drawRightAlignedString(message, Color.WHITE, drawY, graphics2D.getFont(), graphics2D);
                messageCounters.set(i, messageCounters.get(i) - 1);
            } else {
                messages.remove(i);
                messageCounters.remove(i);
            }

            drawY += (int) (gamePanel.tileSize / 1.7);
        }
    }



    public void addMessage(String message) {
        messages.addFirst(message);
        messageCounters.addFirst(150);
    }


}
