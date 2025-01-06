package de.endergamercode.tile;

import de.endergamercode.base.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;
    public int[][] map;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[100];
        getTileImage();
    }

    public void getTileImage() {
            tile[0] = new Tile();
            tile[1] = new Tile();
            tile[2] = new Tile();
            tile[3] = new Tile();
            tile[4] = new Tile();
            tile[5] = new Tile();
            tile[6] = new Tile();
            tile[7] = new Tile();
            tile[8] = new Tile();
            tile[9] = new Tile();

            loadTileImage(10,true);
            loadTileImage(11,false);
            loadTileImage(12,false);
            loadTileImage(13,false);
            loadTileImage(14,false);
            loadTileImage(15,false);
            loadTileImage(16,false);
            loadTileImage(17,false);
            loadTileImage(18,false);
            loadTileImage(19,false);
            loadTileImage(20,false);
            loadTileImage(21,false);
            loadTileImage(22,false);
            loadTileImage(23,false);
            loadTileImage(24,false);
            loadTileImage(25,false);
            loadTileImage(26,false);
            loadTileImage(27,false);
            loadTileImage(30,true);
            loadTileImage(31,true);
    }


    private void loadTileImage(int tileIndex, boolean collision) {
        try {
            tile[tileIndex] = new Tile(); // Initialize the tile
            tile[tileIndex].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + tileIndex + ".png")));
            if(collision){
                tile[tileIndex].collision=true;
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle error in loading image
        }
    }

    public void draw(Graphics2D graphics2D) {
        //draw world
        if (gamePanel.getVariableCamera()) {
            drawMap(graphics2D, "/world/world1.txt");
        } else {
            drawMap(graphics2D, "/world/island.txt");
        }
    }

    private int[][] getMap(String path) {
        int[][] map;
        if (gamePanel.getVariableCamera()) {
            map = new int[10][16];
        } else {
            map = new int[50][50];
        }

        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {

            for (int row = 0; row < map.length; row++) {
                String line = br.readLine();
                if (line == null) break; // Stop if file ends prematurely
                String[] lineValues = line.split(" ");

                for (int col = 0; col < Math.min(lineValues.length, map[row].length); col++) {
                    map[row][col] = Integer.parseInt(lineValues[col]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void drawMap(Graphics2D graphics2D, String path) {
        map = getMap(path);

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int tileIndex = map[row][col];
                int x = gamePanel.tileSize * col;
                int y = gamePanel.tileSize * row;
                if (gamePanel.getVariableCamera()) {
                    graphics2D.drawImage(tile[tileIndex].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
                } else {
                    int screenX = x - gamePanel.player.worldX + gamePanel.player.screenX;
                    int screenY = y - gamePanel.player.worldY + gamePanel.player.screenY;
                    //graphics2D.drawImage(tile[tileIndex].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                    if (x + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                            x - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                            y + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                            y - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                        graphics2D.drawImage(tile[tileIndex].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                    }
                }
            }
        }
    }


}
