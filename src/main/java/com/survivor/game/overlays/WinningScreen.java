package com.survivor.game.overlays;

import com.survivor.engine.entities.Menu;
import com.survivor.game.GAME;
import javafx.scene.control.Button;

public class WinningScreen extends Menu {

    public WinningScreen(double width, double height) {
        super(width, height, "CONGRATULATIONS! YOU WON!");
        Button restart = new Button("Restart");
        restart.setOnAction(e -> {
            System.out.println("Restarting game");
            GAME.getInstance().startNewGame();
            close();
        });
        addButton(restart, 1);
    }
}
