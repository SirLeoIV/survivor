package com.survivor.game.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.math.Layout;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Text;

public class Dot extends Entity {

    Text dot = new Text(".");

    public Dot(Layout layout) {
        super(layout);
        getChildren().add(dot);
    }

    public Dot(Layout layout, long lifeTime) {
        super(layout);
        getChildren().add(dot);
        AnimationTimer timer = new AnimationTimer() {
            long timeLeft = lifeTime;
            long timeStamp = System.currentTimeMillis();
            @Override
            public void handle(long now) {
                if (!GameScene.isGameLoopRunning()) return;
                timeLeft -= System.currentTimeMillis() - timeStamp;
                timeStamp = System.currentTimeMillis();
                if (timeLeft <= 0) {
                    GameScene.removeEntity(Dot.this);
                    stop();
                }
            }
        };
        timer.start();
    }

    @Override
    public void gameUpdate(GameEvent event) {}
}
