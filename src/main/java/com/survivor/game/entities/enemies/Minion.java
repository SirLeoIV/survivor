package com.survivor.game.entities.enemies;

import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.math.Vector2D;

public class Minion extends Enemy implements CollisionListener {

    public Minion(Vector2D position) {
        super(position);
        text.setText("E");
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);
    }

    @Override
    protected double getSpeed() {
        return 200;
    }

    @Override
    protected int getMaxHealth() {
        return 100;
    }
}
