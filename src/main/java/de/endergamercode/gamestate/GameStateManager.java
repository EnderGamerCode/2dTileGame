package de.endergamercode.gamestate;

import de.endergamercode.base.GamePanel;

public class GameStateManager {

    GamePanel gamePanel;
    GameState currentGameState;

    public GameStateManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }



    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
