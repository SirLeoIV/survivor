package com.survivor.engine.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.listener.GameListener;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import static com.survivor.engine.entities.Entity.showHitboxes;

public abstract class Overlay extends Parent implements GameListener {

    private double width;
    private double height;

    public Overlay(double width, double height) {
        attachGameListener();
        setTranslateX((GameScene.getInstance().getScene().getWidth() / 2) - (width / 2));
        setTranslateY(GameScene.getInstance().getScene().getHeight() / 2 - height / 2);
        this.width = width;
        this.height = height;

        if (showHitboxes) {
            Pane hitbox = new Pane();
            hitbox.setLayoutX(0);
            hitbox.setLayoutY(0);
            hitbox.setPrefWidth(width);
            hitbox.setPrefHeight(height);
            hitbox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: transparent;");
            getChildren().add(hitbox);
        }
    }

    public void remove() {
        GameScene.removeOverlay(this);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
