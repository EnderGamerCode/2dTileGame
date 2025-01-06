package de.endergamercode.object;

import de.endergamercode.base.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class KeyObject extends Object{

    public KeyObject(GamePanel gamePanel) {
        super(gamePanel);
        canBeDrawn = true;
        name = "key";
        description = "A key! But what will it be used for?";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/keyRaw.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
