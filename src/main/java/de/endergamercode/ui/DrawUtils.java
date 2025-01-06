package de.endergamercode.ui;

import de.endergamercode.base.GamePanel;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class DrawUtils {

    GamePanel gamePanel;

    public DrawUtils(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void drawTitle(String text, Color titleColor, Font font, Graphics2D graphics2D) {
        graphics2D.setFont(font);
        int x = getCenteredTextX(graphics2D, text);
        int y = getCenteredTextY(graphics2D, text);
        drawOutlinedText(text, x, y, graphics2D);
    }

    public void drawXCenteredString(String text, Color color, int y, Font font, Graphics2D graphics2D) {
        graphics2D.setFont(font);
        int x = getCenteredTextX(graphics2D, text);
        graphics2D.drawString(text, x, y);
    }

    public void drawLeftAlignedString(String text, Color color, int y, Font font, Graphics2D graphics2D){
        graphics2D.setFont(font);
        int x = gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
    }

    public void drawRightAlignedString(String text, Color color, int y, Font font, Graphics2D graphics2D){
        graphics2D.setFont(font);
        graphics2D.setColor(color);
        int x = (int) (gamePanel.screenWidth-gamePanel.tileSize*3.5);
        drawOutlinedText(text, x, y,graphics2D);
    }

    public void drawOutlinedText(String text, long x, long y, Graphics2D graphics2D) {
        AffineTransform originalTransform = graphics2D.getTransform();

        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        graphics2D.setTransform(transform);

        graphics2D.setColor(Color.black);
        FontRenderContext frc = graphics2D.getFontRenderContext();
        TextLayout tl = new TextLayout(text, graphics2D.getFont(), frc);
        Shape shape = tl.getOutline(null);
        graphics2D.setStroke(new BasicStroke(6f));
        graphics2D.draw(shape);

        graphics2D.setColor(Color.white);
        graphics2D.fill(shape);

        graphics2D.setTransform(originalTransform);
    }

    public int getCenteredTextX(Graphics2D graphics2D, String text) {
        int stringLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return (gamePanel.screenWidth - stringLength) / 2;
    }

    public int getCenteredTextY(Graphics2D graphics2D, String text) {
        int stringHeight = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getHeight();
        return (gamePanel.screenHeight - stringHeight) / 2 + graphics2D.getFontMetrics().getAscent();
    }

    public void drawOutlinedSubWindow(Graphics2D graphics2D,int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 120);  // R,G,B, alfa(opacity)
        graphics2D.setColor(c);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        graphics2D.setColor(c);
        graphics2D.setStroke(new BasicStroke(3));    // 5 = width of outlines of graphics
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawSubWindow(Graphics2D graphics2D,int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 120);  // R,G,B, alfa(opacity)
        graphics2D.setColor(c);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        //c = new Color(255, 255, 255);
        //graphics2D.setColor(c);
        //graphics2D.setStroke(new BasicStroke(3));    // 5 = width of outlines of graphics
        //graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }


}
