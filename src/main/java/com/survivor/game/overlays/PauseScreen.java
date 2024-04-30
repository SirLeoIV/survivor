package com.survivor.game.overlays;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Menu;
import com.survivor.game.GAME;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PauseScreen extends Menu {

    public PauseScreen(double width, double height) {
        super(width, height, "PAUSE");
        Button restart = new Button("Restart");
        Button resume = new Button("Resume");
        Button fullscreen = new Button("Toggle Fullscreen");
        restart.setOnAction(e -> {
            System.out.println("Restarting game");
            GAME.getInstance().startNewGame();
            close();
        });
        resume.setOnAction(e -> {
            close();
        });
        fullscreen.setOnAction(e -> {
            ((Stage) GameScene.getInstance().getScene().getWindow())
                    .setFullScreen(!((Stage) GameScene.getInstance().getScene().getWindow()).isFullScreen());
            GameScene.setOverlay(new PauseScreen(width, height));
        });
        addButton(resume, 1);
        addButton(restart, 2);
        addButton(fullscreen, 3);
    }

    @Override
    public void refresh() {
        GameScene.setOverlay(new PauseScreen(getWidth(), getHeight()));
    }
}
