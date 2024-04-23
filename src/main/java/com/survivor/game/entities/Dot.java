package com.survivor.game.entities;

import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.math.Layout;
import javafx.scene.text.Text;

public class Dot extends Entity {

    Text dot = new Text(".");

    public Dot(Layout layout) {
        super(layout);
        getChildren().add(dot);
    }

    @Override
    public void gameUpdate(GameEvent event) {}
}
