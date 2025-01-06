package de.endergamercode;

import de.endergamercode.base.GamePanel;

import javax.swing.*;

public class TileGame {
    public static void main(String[] args) {
        JFrame window = new JFrame("2dTileGame");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        GamePanel panel = new GamePanel();

        panel.setupGame();
        panel.startGameThread();
        panel.requestFocusInWindow();

        window.add(panel);
        window.pack();

        window.setVisible(true);
        window.setLocationRelativeTo(null);


    }
}
