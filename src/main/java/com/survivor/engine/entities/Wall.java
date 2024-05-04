package com.survivor.engine.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Line2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Wall extends Entity {

    public final Line2D edgeUp;
    public final Line2D edgeDown;
    public final Line2D edgeLeft;
    public final Line2D edgeRight;

    public Wall(Layout layout) {
        super(layout);
        edgeUp = new Line2D(
                getUpLeft(),
                getDownRight());
        edgeDown = new Line2D(
                getDownLeft(),
                getUpRight());
        edgeLeft = new Line2D(
                getUpLeft(),
                getDownLeft());
        edgeRight = new Line2D(
                getUpRight(),
                getDownRight());
        Pane box = new Pane();
        box.setLayoutX(0);
        box.setLayoutY(0);
        box.setPrefWidth(layout.getWidth());
        box.setPrefHeight(layout.getHeight());
        box.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: transparent;");
        getChildren().add(box);
    }


    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);
    }

    public static ArrayList<Wall> getWalls() {
        return GameScene.getEntitiesByClass(Wall.class).stream().map(e -> (Wall) e).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

}
