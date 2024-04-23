package com.survivor.engine.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.listener.GameListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public abstract class Entity extends Parent implements GameListener {

    public static boolean showHitboxes = false;
    
    AnimationTimer timer;
    private Layout layout;

    public Entity(Layout layout) {
        attachGameListener();
        GameScene.addEntity(this);
        GameScene.addEntityV2(this);
        setLayout(layout);

        if (showHitboxes) {
            Pane hitbox = new Pane();
            hitbox.setLayoutX(0);
            hitbox.setLayoutY(0);
            hitbox.setPrefWidth(layout.getWidth());
            hitbox.setPrefHeight(layout.getHeight());
            hitbox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: transparent;");
            getChildren().add(hitbox);
        }

        timer = new AnimationTimer() {
            long lastCall = 0;
            @Override
            public void handle(long now) {
                if (lastCall == 0) lastCall = now / 1000000;
                if(GameScene.isGameLoopRunning()) process(now / 1000000 - lastCall);
                lastCall = now / 1000000;
            }
        };
        timer.start();
    }

    private void setLayout(Layout layout) {
        this.layout = layout;
        setX(layout.getX());
        setY(layout.getY());
        setRotation(layout.getRotation());
    }

    public void setPosition(Vector2D position) {
        setX(position.getX());
        setY(position.getY());
    }

    public Vector2D getPosition() {
        return new Vector2D(getX(), getY());
    }

    public void process(long millisPassed) {}

    public void setX(double x) {
        setTranslateX(x);
    }

    public void setY(double y) {
        setTranslateY(y);
    }

    public void setZ(double z) {
        setTranslateZ(z);
    }

    public double getX() {
        return getTranslateX();
    }

    public double getY() {
        return getTranslateY();
    }

    public double getZ() {
        return getTranslateZ();
    }

    public Layout getLayout() {
        return new Layout(getX(), getY(), layout.getWidth(), layout.getHeight(), getRotate());
    }

    @Deprecated // super weird
    public void setRotation(double rotation) {
        setRotate(rotation);
    }

    public void stopTimer() {
        timer.stop();
    }

    public boolean isColliding(Entity other) {
        Vector2D[] corners = new Vector2D[] {
            new Vector2D(getX(), getY()),
            new Vector2D(getX() + getLayout().getWidth(), getY()),
            new Vector2D(getX(), getY() + getLayout().getHeight()),
            new Vector2D(getX() + getLayout().getWidth(), getY() + getLayout().getHeight())
        };
        for (Vector2D corner : corners) {
            if (corner.getX() > other.getX() && corner.getX() < other.getX() + other.getLayout().getWidth() &&
                corner.getY() > other.getY() && corner.getY() < other.getY() + other.getLayout().getHeight()) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingV2(Entity other) {
        Vector2D distance = Vector2D.subtract(getLayout().getCenter(), other.getLayout().getCenter()).absolute();
        Vector2D combinedWidth = Vector2D.add(
                new Vector2D(getLayout().getWidth() / 2, getLayout().getHeight() / 2),
                new Vector2D(other.getLayout().getWidth() / 2, other.getLayout().getHeight() / 2)
        );
        Vector2D overlap = Vector2D.subtract(
                distance, combinedWidth);
        return overlap.getX() < 0 && overlap.getY() < 0;
    }
}
