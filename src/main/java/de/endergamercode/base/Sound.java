package de.endergamercode.base;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.Objects;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];

    //SOUNDS
    public static final int CHILL_MUSIC = 0,
            CHEST_OPEN = 1,
            CHEST_CLOSE = 2,
            INVENTORY_OPEN = 3,
            KEY_PICKUP = 4,
            CONTAINER_MOVE = 5,
            CONTAINER_SELECT = 6,
            ITEM_PICKUP = 7;

    public Sound() {
        soundURL[0] = Objects.requireNonNull(getClass().getResource("/sound/musicLoopChill.wav"));
        soundURL[1] = Objects.requireNonNull(getClass().getResource("/sound/chestOpening.wav"));
        soundURL[2] = Objects.requireNonNull(getClass().getResource("/sound/chestClosing.wav"));
        soundURL[3] = Objects.requireNonNull(getClass().getResource("/sound/inventory_open.wav"));
        soundURL[4] = Objects.requireNonNull(getClass().getResource("/sound/pickupKey.wav"));
        soundURL[5] = Objects.requireNonNull(getClass().getResource("/sound/containerMove.wav"));
        soundURL[6] = Objects.requireNonNull(getClass().getResource("/sound/containerSelect.wav"));
        soundURL[7] = Objects.requireNonNull(getClass().getResource("/sound/itemPickup.wav"));
    }

    public void setFile(int i){

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

}
