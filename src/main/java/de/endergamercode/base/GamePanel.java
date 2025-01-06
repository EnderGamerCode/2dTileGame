package de.endergamercode.base;

import de.endergamercode.entity.Player;
import de.endergamercode.gamestate.GameState;
import de.endergamercode.gamestate.GameStateManager;
import de.endergamercode.object.Object;
import de.endergamercode.tile.TileManager;
import de.endergamercode.ui.UI;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    final int FPS = 60;
    final int originalTileSize = 32;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 10;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    Sound music = new Sound();
    public Sound soundEffect = new Sound();
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public AssetManager assetManager = new AssetManager(this);
    public GameStateManager gameStateManager = new GameStateManager(this);

    //Managers and handlers
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    ClickHandler clickHandler = new ClickHandler(this);

    //ENTITY AND OBJECT
    public Player player = new Player(this);
    public Object[] objects = new Object[10];
    public UI ui = new UI(this);



    Thread gamethread;


    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK.brighter());
        setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(clickHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void setupGame() {
        gameStateManager.setCurrentGameState(GameState.PLAYING);
        assetManager.setObject();
        playMusic(0);
    }

    public void startGameThread() {
        gamethread = new Thread(this);
        gamethread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gamethread != null) {
            //Game LOOP

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            //display fps
            /*
            if(timer >= 1000000000){
            System.out.println("FPS:"+drawCount);
            drawCount = 0;
            timer=0;
            }*/
            //

        }
    }

    public void update() {
        if (gameStateManager.getCurrentGameState().equals(GameState.PLAYING)) {
            player.update();
        } else if (gameStateManager.getCurrentGameState().equals(GameState.PAUSED)) {
            //nothing for now
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        tileManager.draw(graphics2D);
        for (Object object : objects) {
            if (object != null) {
                object.draw(graphics2D, this);
            }
        }
        player.draw(graphics2D);
        ui.draw(graphics2D);

        graphics2D.dispose();
    }

    public void setVariableCamera(boolean variableCamera) {
        player.customCamera = variableCamera;
    }

    public boolean getVariableCamera() {
        return player.customCamera;
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }


    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void setMusicVolume(long targetVolume){
        long duration = 250;
        new Thread(() -> {
            try {
                FloatControl volumeControl = (FloatControl) music.clip.getControl(FloatControl.Type.MASTER_GAIN);
                float currentVolume = volumeControl.getValue();
                float step = (targetVolume - currentVolume) / (duration / 10); // 10 ms steps

                for (int i = 0; i < duration / 10; i++) {
                    currentVolume += step;
                    volumeControl.setValue(currentVolume);
                    Thread.sleep(10); // Adjust every 10 ms
                }

                volumeControl.setValue(targetVolume); // Ensure exact target volume
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    long clipTime;
    public void pauseMusic() {
        clipTime = music.clip.getMicrosecondPosition();
        music.stop();

    }

    public void resumeMusic() {
        System.out.println("Resumed at:" + clipTime);
        if(music.clip != null && clipTime > 0){
            music.clip.setMicrosecondPosition(clipTime);
            music.play();
            music.loop();
        }else{
            System.out.println("Music failed to play!");
        }
    }


}
