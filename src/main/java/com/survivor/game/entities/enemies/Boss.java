package com.survivor.game.entities.enemies;

import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.math.Vector2D;
import com.survivor.game.GAME;

public class Boss extends Enemy implements CollisionListener {

    long spawnTime = 7000;
    long timeTillNextSpawn = 0;

    public Boss(Vector2D position) {
        super(position);
        text.setText("B");
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);
        timeTillNextSpawn -= millisPassed;
        if (timeTillNextSpawn <= 0) {
            timeTillNextSpawn = spawnTime;
            for (int i = 0; i < 4; i++) {
                int x = (int) (Math.random() * 150 - 75);
                int y = (int) (Math.random() * 150 - 75);
                GAME.getInstance().getCurrentWave().addEnemy(new Minion(new Vector2D(getLayout().getX() + x, getLayout().getY() + y)));
            }
        }
    }

    @Override
    protected double getSpeed() {
        return 150;
    }

    @Override
    protected int getMaxHealth() {
        return 300;
    }
}
