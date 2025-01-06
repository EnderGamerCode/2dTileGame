package de.endergamercode.ui;

import de.endergamercode.base.GamePanel;
import de.endergamercode.object.Object;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static java.awt.SystemColor.text;

public class Container {

    public String containerName;
    public int maxRows;
    public int maxColumns;
    public int containerX;
    public int containerY;
    public int containerWidth;
    public int containerHeight;
    public int slotCol = 0;
    public int slotRow = 0;
    public ContainerType type; // NEW: Type identifier
    Graphics2D graphics2D;
    public GamePanel gamePanel;
    ArrayList<Object> items;
    int padding = 20;




    public Container(String containerName, int maxRows, int maxColumns, ContainerType type ,int containerX, int containerY, ArrayList<Object> items, GamePanel gamePanel) {
        this.containerName = containerName;
        this.maxRows = maxRows;
        this.maxColumns = maxColumns;
        this.containerY = containerY;
        this.gamePanel = gamePanel;
        this.items = items;
        this.type = type;
        this.containerX = containerX;
        containerWidth = maxColumns * gamePanel.tileSize + 2*padding;
        containerHeight = maxRows * gamePanel.tileSize + 2*padding;
    }

    public Container(String containerName, int maxRows, int maxColumns, ContainerType type ,int containerY, ArrayList<Object> items, GamePanel gamePanel) {
        this(containerName,maxRows,maxColumns,type,0,containerY,items,gamePanel);
        containerX = gamePanel.screenWidth/2-containerWidth/2;
    }

    public void draw(){

        Font font = graphics2D.getFont();
        graphics2D.setFont(graphics2D.getFont().deriveFont(150f));
        if (type.equals(ContainerType.INVENTORY)) {
            gamePanel.ui.drawUtils.drawOutlinedText(containerName, (long) (containerX+(containerWidth-graphics2D.getFontMetrics().getStringBounds(containerName,graphics2D).getWidth())/2), 150,graphics2D);
        }else {
            gamePanel.ui.drawUtils.drawOutlinedText(containerName,gamePanel.ui.drawUtils.getCenteredTextX(graphics2D, containerName),150,graphics2D);
           // graphics2D.drawString(containerName, , );
        }
        graphics2D.setFont(font);

        //DRAW BACKGROUND
        drawSubWindow(containerX,containerY,containerWidth,containerHeight);

        //SLOTS
        final int slotXstart = containerX+padding;
        final int slotYstart = containerY+padding;
        int slotX = slotXstart;
        int slotY = slotYstart;

        //DRAW ITEMS
        for (int i = 0; i < items.size(); i++) {
            graphics2D.drawImage(items.get(i).image, slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, null);

            slotX += gamePanel.tileSize;

            if ((i + 1) % maxColumns == 0) {
                slotX = slotXstart;
                slotY += gamePanel.tileSize;
            }
        }

        //CURSOR
        int cursorX = slotXstart + (gamePanel.tileSize * slotCol);
        int cursorY = slotYstart + (gamePanel.tileSize * slotRow);
        int cursorWidth = gamePanel.tileSize;
        int cursorHeight = gamePanel.tileSize;

        //DRAW CURSOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        if(type.equals(ContainerType.INVENTORY)){
            //DESCRIPTION FRAME
            int dFrameWidth = gamePanel.tileSize*11+40;
            int dFrameHeight = (int) (gamePanel.tileSize*1.5);
            int dFrameX = containerX;
            int dFrameY = gamePanel.tileSize*8+45;

            drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

            //DESCRIPTION TEXT
            int itemIndex = getItemIndexOnSlot();

            if(itemIndex < gamePanel.player.inventory.getItemList().size()){
                graphics2D.setFont(gamePanel.ui.smallFont.deriveFont(60f));
                String description = gamePanel.player.inventory.getItemList().get(itemIndex).description;
                int textX = (int) (dFrameX+(dFrameWidth-graphics2D.getFontMetrics().getStringBounds(description,graphics2D).getWidth())/2);
                int textY = dFrameY + gamePanel.tileSize;
                graphics2D.drawString(description,textX,textY);
            }
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0,210);  // R,G,B, alfa(opacity)
        graphics2D.setColor(c);
        graphics2D.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        graphics2D.setColor(c);
        graphics2D.setStroke(new BasicStroke(5));    // 5 = width of outlines of graphics
        graphics2D.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }



    public void setItems(ArrayList<Object> items) {
        this.items = items;
    }

    public void moveCursor(int deltaRow, int deltaCol) {
        slotRow = Math.max(0, Math.min(slotRow + deltaRow, maxRows - 1));
        slotCol = Math.max(0, Math.min(slotCol + deltaCol, maxColumns - 1));
    }

    public Object getSelectedItem() {
        int index = slotRow * maxColumns + slotCol;
        if (index < items.size()) {
            return items.get(index);
        }
        return null; // No item selected
    }

    public int getItemIndexOnSlot(){
        return slotCol + (slotRow*5);
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }
}

