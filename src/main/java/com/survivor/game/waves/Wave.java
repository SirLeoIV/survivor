package com.survivor.game.waves;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Banner;
import com.survivor.engine.entities.Character;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.events.GameEventType;
import com.survivor.engine.listener.GameListener;
import com.survivor.engine.math.Vector2D;
import com.survivor.game.entities.enemies.Boss;
import com.survivor.game.entities.enemies.Minion;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Wave implements GameListener {

    AnimationTimer timer;
    ArrayList<Character> enemies = new ArrayList<>();

    long timeLeft = 0;
    boolean timed = false;

    int numMinions = 0;
    int numBosses = 0;

    String title;

    public Wave(String title, int numMinions, int numBosses) {
        this.title = title;
        this.numMinions = numMinions;
        this.numBosses = numBosses;
        timer = new AnimationTimer() {
            long lastCall = 0;
            @Override
            public void handle(long now) {
                if (!GameScene.isGameLoopRunning()) return;
                if (lastCall == 0) lastCall = now / 1000000;
                long millisPassed = now / 1000000 - lastCall;
                lastCall = now / 1000000;
                if (timed) {
                    timeLeft -= millisPassed;
                    if (timeLeft <= 0) {
                        endWave();
                    }
                }
            }
        };
    }

    public void startWave() {
        System.out.println("Starting wave");
        new Banner(title, 2000);
        for (int i = 0; i < numMinions; i++) {
            enemies.add(new Minion(new Vector2D(
                    Math.random() * GameScene.getInstance().getScene().getWidth(),
                    Math.random() * GameScene.getInstance().getScene().getHeight())
            ));
        }
        for (int i = 0; i < numBosses; i++) {
            enemies.add(new Boss(new Vector2D(
                     Math.random() * GameScene.getInstance().getScene().getWidth(),
                     Math.random() * GameScene.getInstance().getScene().getHeight())
            ));
        }
        GameScene.attachGameListener(this);
        timer.start();
    };

    public boolean isWaveOver() {
        return enemies.isEmpty();
    };

    public void endWave() {
        System.out.println("Ending wave");
        removeAllEnemies();
        timer.stop();
    };

    public void removeEnemy(Character enemy) {
        enemies.remove(enemy);
    }

    public void removeAllEnemies() {
        enemies.clear();
    }

    public ArrayList<Character> getEnemies() {
        return enemies;
    }

    public void addEnemies(ArrayList<Character> enemies) {
        this.enemies.addAll(enemies);
    }

    public void addEnemy(Character enemy) {
        enemies.add(enemy);
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
        this.timed = true;
    }

    @Override
    public void gameUpdate(GameEvent event) {
        if (event.getType() == GameEventType.REMOVE_ENTITY) {
            enemies.remove(event.getData());
        }
    }
}
