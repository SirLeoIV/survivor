package com.survivor.game;

import com.survivor.engine.GameScene;
import com.survivor.engine.math.Layout;
import com.survivor.game.entities.*;
import com.survivor.game.overlays.GameOverScreen;
import com.survivor.game.overlays.UpgradeScreen;
import com.survivor.game.overlays.WinningScreen;
import com.survivor.game.waves.Wave;
import javafx.animation.AnimationTimer;

public class GAME {

    AnimationTimer timer;
    Wave currentWave;

    Wave wave1 = new Wave("Wave 1", 5);
    Wave wave2 = new Wave("Wave 2", 10);
    Wave wave3 = new Wave("Wave 3", 15);
    Wave wave4 = new Wave("Wave 4", 25);
    Wave wave5 = new Wave("Wave 5", 40);

    private static GAME instance;

    public static GAME getInstance() {
        if (instance == null) {
            instance = new GAME();
        }
        return instance;
    }

    public static GAME setInstance() {
        instance = new GAME();
        return instance;
    }

    public void startNextWave() {
        currentWave.startWave();
    }

    public void startNewGame() {
        GameScene.removeAllEntities();
        StateMachine.resetInstance();
        Player player = new Player(new Layout(200, 200, 27, 35, 0));
        Gun gun = new Gun(new Layout(200, 200, 60, 20, 0));
        Score score = new Score();
        FPS fps = new FPS();
        Coordinates coordinates = new Coordinates(player, new Layout(10, 50, 1, 1, 0));
        Stats stats = new Stats();
        Stats.refresh();
        currentWave = wave1;
        currentWave.startWave();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!GameScene.isGameLoopRunning()) return;
                if (player.getHealth() <= 0) {
                    GameScene.setOverlay(new GameOverScreen(500, 400));
                    stop();
                    return;
                }
                if (StateMachine.getInstance().money >= 5) {
                    GameScene.setOverlay(new UpgradeScreen(500, 400));
                    return;
                }
                if (currentWave.isWaveOver()) {
                    if (currentWave == wave1) {
                        currentWave = wave2;
                    } else if (currentWave == wave2) {
                        currentWave = wave3;
                    } else if (currentWave == wave3) {
                        currentWave = wave4;
                    } else if (currentWave == wave4) {
                        currentWave = wave5;
                    } else {
                        GameScene.setOverlay(new WinningScreen(500, 400));
                        stop();
                        timer.stop();
                        return;
                    }
                    currentWave.startWave();
                }
            }
        };
        timer.start();
    }
}
