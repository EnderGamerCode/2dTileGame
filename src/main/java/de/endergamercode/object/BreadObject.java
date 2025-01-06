package de.endergamercode.object;

import de.endergamercode.base.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BreadObject extends Object {
    public BreadObject(GamePanel gamePanel) {
        super(gamePanel);
        name = "bread";
        description = "Mmh! Bread!";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/bread.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
