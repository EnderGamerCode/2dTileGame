package de.endergamercode.entity;

import de.endergamercode.base.GamePanel;
import de.endergamercode.base.KeyHandler;
import de.endergamercode.base.Sound;
import de.endergamercode.gamestate.GameState;
import de.endergamercode.object.ChestObject;
import de.endergamercode.object.KeyObject;
import de.endergamercode.object.Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{

    public final int screenX;
    public final int screenY;
    public boolean customCamera = false ;
    public Inventory inventory;    //public final int maxInventorySize = 20;

    //inventory
    public int keyCount = 0;

    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.keyHandler = gamePanel.keyHandler;

        screenX = gamePanel.screenWidth/2-(gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight/2-(gamePanel.tileSize/2);

        solidArea = new Rectangle(32,64,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        inventory = new Inventory(this);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = 15 * gamePanel.tileSize;
        worldY =  13 * gamePanel.tileSize;
        speed = 5;
    }


    public void getPlayerImage(){
        try{
            //initialize images here
            BufferedImage walkRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PinkMonster/PinkMonsterWalkRight.png")));
            walkR1 = walkRight.getSubimage(0,0,32,32);
            walkR2 = walkRight.getSubimage(64,0,32,32);
            walkR3 = walkRight.getSubimage(128,0,32,32);
            walkR4 = walkRight.getSubimage(160,0,32,32);
            BufferedImage walkLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PinkMonster/PinkMonsterWalkLeft.png")));
            walkL1 = walkLeft.getSubimage(160,0,32,32);
            walkL2 = walkLeft.getSubimage(128,0,32,32);
            walkL3 = walkLeft.getSubimage(64,0,32,32);
            walkL4 = walkLeft.getSubimage(0,0,32,32);
            BufferedImage idleR = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PinkMonster/PinkMonsterIdleRight.png")));
            idleR1 = idleR.getSubimage(0,0,32,32);
            idleR2 = idleR.getSubimage(32,0,32,32);
            idleR3 = idleR.getSubimage(64,0,32,32);
            idleR4 = idleR.getSubimage(96,0,32,32);
            BufferedImage idleL = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PinkMonster/PinkMonsterIdleLeft.png")));
            idleL1 = idleL.getSubimage(0,0,32,32);
            idleL2 = idleL.getSubimage(32,0,32,32);
            idleL3 = idleL.getSubimage(64,0,32,32);
            idleL4 = idleL.getSubimage(96,0,32,32);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        //set animation direction
        if(keyHandler.upPressed){
            setLastDirection();
        }else if(keyHandler.downPressed){
            setLastDirection();
        }else if(keyHandler.leftPressed){
            direction="left";
        }else if(keyHandler.rightPressed){
            direction="right";
        }else if(keyHandler.leftReleased){
            direction="idleLeft";
        }else if(keyHandler.rightReleased){
            direction="idleRight";
        }

        //check tile collision
        colliding = false;
        gamePanel.collisionHandler.checkTile(this);
        int objectIndex = gamePanel.collisionHandler.checkObject(this,true);
        pickupObject(objectIndex);
        if (!colliding) {
            int deltaX = 0, deltaY = 0;

            if (keyHandler.upPressed) deltaY = -speed;
            else if (keyHandler.downPressed) deltaY = speed;
            else if (keyHandler.leftPressed) deltaX = -speed;
            else if (keyHandler.rightPressed) deltaX = speed;

            worldX += deltaX;
            worldY += deltaY;

        }
        //cycle Animation
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum % 4) + 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g){
        //animate
        BufferedImage image = switch (direction) {
            case "right" -> switch (spriteNum) {
                case 1 -> walkR1;
                case 2 -> walkR2;
                case 3 -> walkR3;
                default -> walkR4;
            };
            case "left" -> switch (spriteNum) {
                case 1 -> walkL1;
                case 2 -> walkL2;
                case 3 -> walkL3;
                default -> walkL4;
            };
            case "idleLeft" -> switch (spriteNum) {
                case 1 -> idleL1;
                case 2 -> idleL2;
                case 3 -> idleL3;
                default -> idleL4;
            };
            case "idleRight" -> switch (spriteNum) {
                case 1 -> idleR1;
                case 2 -> idleR2;
                case 3 -> idleR3;
                default -> idleR4;
            };
            default -> null;
        };

        //draw player
        if(customCamera) {
            g.drawImage(image, worldX, worldY, gamePanel.tileSize, gamePanel.tileSize, null);
        }else {
            g.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
        //g.dispose();
    }

    public void pickupObject(int index){
        if(index!=-1){
            Object object = gamePanel.objects[index];
            String name = object.name;
            switch (name){
                case "key":
                    keyCount++;
                    gamePanel.objects[index] = null;
                    System.out.println("Key count: "+keyCount);
                    inventory.add(object);
                    gamePanel.playSoundEffect(Sound.KEY_PICKUP);
                    break;
                case "chest":
                    gamePanel.gameStateManager.setCurrentGameState(GameState.CHEST);
                    gamePanel.ui.setChest((ChestObject) object);
                    break;
            }
        }
    }

    private void setLastDirection(){
        if(keyHandler.getLastPressed().equals("A")){
            direction="left";
        } else if(keyHandler.getLastPressed().equals("D")){
            direction="right";
        }
    }



}
