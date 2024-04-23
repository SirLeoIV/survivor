package com.survivor.game.overlays;

import com.survivor.engine.entities.Menu;
import com.survivor.game.GAME;
import javafx.scene.control.Button;

public class GameOverScreen extends Menu {

    public GameOverScreen(double width, double height) {
        super(width, height, "GAME OVER");
        Button restart = new Button("Restart");
        restart.setOnAction(e -> {
            System.out.println("Restarting game");
            GAME.getInstance().startNewGame();
            close();
        });
        addButton(restart, 1);
    }
}
