package com.survivor;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.math.Layout;
import com.survivor.game.*;
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
        stage.setScene(scene);
        stage.show();


        Player player = new Player(new Layout(200, 200, 27, 35, 0));
        Enemy enemy = new Enemy(new Layout(400, 400, 27, 35, 0));
        Gun gun = new Gun(new Layout(200, 200, 2, 2, 0));
        FPS fps = new FPS();
        Score score = new Score();

    }

    public static void main(String[] args) {
        launch();
    }
}