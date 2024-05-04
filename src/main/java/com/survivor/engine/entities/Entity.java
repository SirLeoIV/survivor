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
        return getOverlapX(other) >= 0 && getOverlapY(other) >= 0;
    }

    public Vector2D getUpLeft() {
        return new Vector2D(getX(), getY());
    }

    public Vector2D getUpRight() {
        return new Vector2D(getX() + getLayout().getWidth(), getY());
    }

    public Vector2D getDownRight() {
        return new Vector2D(getX() + getLayout().getWidth(), getY() + getLayout().getHeight());
    }

    public Vector2D getDownLeft() {
        return new Vector2D(getX(), getY() + getLayout().getHeight());
    }

    public double getOverlapX(Entity other) {
        return - Math.max(getX(), other.getX()) + Math.min(getX() + getLayout().getWidth(), other.getX() + other.getLayout().getWidth());
    }

    public double getOverlapY(Entity other) {
        return - Math.max(getY(), other.getY()) + Math.min(getY() + getLayout().getHeight(), other.getY() + other.getLayout().getHeight());
    }
}
