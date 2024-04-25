package com.survivor.game.entities;

import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.math.Layout;
import javafx.scene.text.Text;

public class Coordinates extends Entity {


    Text text = new Text("x: 0; y: 0");
    Entity entity;

    public Coordinates(Entity entity, Layout layout) {
        super(layout);
        this.entity = entity;
        getChildren().add(text);
    }


    private void updateText() {
        String className = entity.getClass().getName().split("\\.")[entity.getClass().getName().split("\\.").length - 1];
        text.setText(className  +  ": x: " + (int) entity.getLayout().getX() + "; y: " + (int) entity.getLayout().getY());
    }

    @Override
    public void process(long millisPassed) {
         super.process(millisPassed);
         updateText();
    }

    @Override
    public void gameUpdate(GameEvent event) {}
}
