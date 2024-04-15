package com.survivor.engine.entities;

import com.survivor.engine.events.GameEvent;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;

public class Character extends Entity {

    protected Vector2D velocity = new Vector2D(0, 0);
    protected Vector2D acceleration = new Vector2D(0, 0);
    protected Vector2D direction = new Vector2D(0, 0);
    protected double speed = 300;

    public Character(Layout layout) {
        super(layout);
    }

    @Override
    public void gameUpdate(GameEvent event) {}

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);
        if (direction.getMagnitude() > 0) {
            velocity = direction.scaleTo(speed);
        }
        try {
            velocity.cutToMagnitude(velocity.getMagnitude() * 0.95 / (60d / (1000d / millisPassed)));
        } catch (ArithmeticException ignore) {}
        move(velocity.getX() * millisPassed / 1000, velocity.getY() * millisPassed / 1000);
        velocity.add(new Vector2D(acceleration.getX() * millisPassed / 1000, acceleration.getY() * millisPassed / 1000));
    }

    protected void move(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
    }


    protected void moveUp() {
        direction.setY(-1);
    }

    protected void moveDown() {
        direction.setY(1);
    }

    protected void moveLeft() {
        direction.setX(-1);
    }

    protected void moveRight() {
        direction.setX(1);
    }
}
