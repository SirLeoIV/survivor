package com.survivor;

import com.survivor.engine.GameScene;
import com.survivor.game.GAME;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) {
        GameScene root = GameScene.getInstance();
        Scene scene = new Scene(root, 1000, 600);
        root.initialize();
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        GAME game = GAME.setInstance();
        game.startNewGame();
    }

    public static void main(String[] args) {
        launch();
    }
}