package de.endergamercode.entity;

import de.endergamercode.base.GamePanel;
import de.endergamercode.base.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public int worldX, worldY;
    public int speed;
    public String direction = "idleRight";

    public BufferedImage walkR1,walkR2,walkR3,walkR4,walkL1,walkL2,walkL3,walkL4,idleL1,idleL2,idleL3,idleL4,idleR1,idleR2,idleR3,idleR4;
    public int spriteNum = 1;
    public int spriteCounter = 0;
    public Rectangle solidArea;
    public int solidAreaDefaultX,solidAreaDefaultY;
    public boolean colliding = false;

}
