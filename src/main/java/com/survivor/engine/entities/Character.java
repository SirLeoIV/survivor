package com.survivor.engine.entities;

import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Line2D;
import com.survivor.engine.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Entity {

    protected Vector2D velocity = new Vector2D(0, 0);
    protected Vector2D acceleration = new Vector2D(0, 0);
    protected Vector2D direction = new Vector2D(0, 0);

    protected Vector2D previousPosition;

    protected boolean touchingWall = false;

    public Character(Layout layout) {
        super(layout);
        previousPosition = new Vector2D(getX(), getY());
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);
        if (direction.getMagnitude() > 0) {
            velocity = direction.scaleTo(getSpeed());
        }
        try {
            velocity.cutToMagnitude(velocity.getMagnitude() * 0.95 / (60d / (1000d / millisPassed)));
        } catch (ArithmeticException ignore) {}
        move(velocity.getX() * millisPassed / 1000, velocity.getY() * millisPassed / 1000);
        velocity.add(new Vector2D(acceleration.getX() * millisPassed / 1000, acceleration.getY() * millisPassed / 1000));
    }

    protected void move(double x, double y) {
        previousPosition.update(getX(), getY());

        double newX = getX() + x;
        double newY = getY() + y;
        setX(newX);
        setY(newY);

        checkBounds();
    }

    private void checkBounds() {
        touchingWall = false;

        if (getX() < 0) setX(0);
        if (getY() < 0) setY(0);
        if (getX() > getScene().getWidth() - getLayout().getWidth()) setX(getScene().getWidth() - getLayout().getWidth());
        if (getY() > getScene().getHeight() - getLayout().getHeight()) setY(getScene().getHeight() - getLayout().getHeight());

        for (Wall wall : Wall.getWalls()) {
            if (wall.isColliding(this)) {
                touchingWall = true;
                if (wall.getOverlapX(this) > wall.getOverlapY(this)) {
                    if (getLayout().getCenter().getY() < wall.getLayout().getCenter().getY()) { // up
                        setY(getY() - (wall.getOverlapY(this) + 1));
                    } else { // down
                        setY(getY() + (wall.getOverlapY(this) + 1));
                    }
                } else {
                    if (getLayout().getCenter().getX() < wall.getLayout().getCenter().getX()) { // left
                        setX(getX() - (wall.getOverlapX(this) + 1));
                    } else { // right
                        setX(getX() + (wall.getOverlapX(this) + 1));
                    }
                }
            }
        }

        ArrayList<Line2D> diffs = getDiffLines();

        double margin2 = 1;
        for (Wall wall : Wall.getWalls()) {
            for (Line2D diff : diffs) {
                boolean intersecting = false;
                if (diff.intersects(wall.edgeUp)) {
                    setY(previousPosition.getY() - margin2);
                    intersecting = true;
                }
                if (diff.intersects(wall.edgeDown)) {
                    setY(previousPosition.getY() + margin2);
                    intersecting = true;
                }
                if (diff.intersects(wall.edgeLeft)) {
                    setX(previousPosition.getX() - margin2);
                    intersecting = true;
                }
                if (diff.intersects(wall.edgeRight)) {
                    setX(previousPosition.getX() + margin2);
                    intersecting = true;
                }
                if (intersecting) {
                    touchingWall = true;
                    return;
                }
            }
        }

    }

    private ArrayList<Line2D> getDiffLines() {
        double margin = 2;
        Line2D prevUpLeftDiff = new Line2D(
                new Vector2D(previousPosition.getX(), previousPosition.getY()),
                new Vector2D(getX() + margin, getY() + margin));
        Line2D prevUpRightDiff = new Line2D(
                new Vector2D(previousPosition.getX() + getLayout().getWidth(), previousPosition.getY()),
                new Vector2D(getX() + getLayout().getWidth() - margin, getY() + margin));
        Line2D prevDownLeftDiff = new Line2D(
                new Vector2D(previousPosition.getX(), previousPosition.getY() + getLayout().getHeight()),
                new Vector2D(getX() + margin, getY() + getLayout().getHeight() - margin));
        Line2D prevDownRightDiff = new Line2D(
                new Vector2D(previousPosition.getX() + getLayout().getWidth(), previousPosition.getY() + getLayout().getHeight()),
                new Vector2D(getX() + getLayout().getWidth() - margin, getY() + getLayout().getHeight() - margin));
        return new ArrayList<>(List.of(
                prevUpLeftDiff,
                prevUpRightDiff,
                prevDownLeftDiff,
                prevDownRightDiff));
    }

    protected abstract double getSpeed();

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
