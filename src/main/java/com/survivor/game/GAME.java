package com.survivor.game;

import com.survivor.engine.GameScene;
import com.survivor.engine.math.Layout;
import com.survivor.game.entities.*;
import com.survivor.game.overlays.GameOverScreen;
import com.survivor.game.overlays.UpgradeScreen;
import com.survivor.game.waves.Wave;
import javafx.animation.AnimationTimer;

public class GAME {

    AnimationTimer timer;

    private Wave currentWave;
    private int waveNumber;

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

    public Wave getCurrentWave() {
        return currentWave;
    }

    public void startNewGame() {
        GameScene.removeAllEntities();
        StateMachine.resetInstance();
        Player player = new Player(new Layout(200, 200, 27, 35, 0));
        new Gun(new Layout(200, 200, 60, 20, 0));
        new Score();
        new FPS();
        new Coordinates(player, new Layout(10, 50, 1, 1, 0));
        new Stats();
        Stats.refresh();
        waveNumber = 1;
        currentWave = new Wave("Wave 1", 5, 0);
        currentWave.startWave();
        GameScene.setOverlay(new UpgradeScreen(500, 500, 1));

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!GameScene.isGameLoopRunning()) return;
                if (player.getHealth() <= 0) {
                    GameScene.setOverlay(new GameOverScreen(500, 400));
                    stop();
                    return;
                }
                if (currentWave.isWaveOver()) {
                    waveNumber++;
                    StateMachine.getInstance().wave = waveNumber;
                    Stats.refresh();
                    if (waveNumber % 2 == 1) {
                        currentWave = new Wave("Wave " + (waveNumber + 1) / 2, waveNumber * 5, 0);
                    } else {
                        currentWave = new Wave("Boss " + waveNumber / 2, 0, waveNumber);
                    }

                    currentWave.startWave();
                    GameScene.setOverlay(new UpgradeScreen(500, 500, waveNumber));
                }
            }
        };
        timer.start();
    }
}
