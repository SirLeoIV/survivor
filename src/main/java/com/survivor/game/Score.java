package com.survivor.game;

import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.math.Layout;
import javafx.scene.text.Text;

public class Score extends Entity {


    static int score = 0;
    static Text scoreText = new Text("Score: 0");

    public Score() {
        super(new Layout(10, 30, 1, 1, 0));
        getChildren().add(scoreText);
    }

    public static void increaseScore() {
        score++;
        scoreText.setText("Score: " + score);
    }

    @Override
    public void gameUpdate(GameEvent event) {}
}
