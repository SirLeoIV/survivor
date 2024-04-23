package com.survivor.game;

import com.survivor.engine.entities.Entity;
import com.survivor.engine.math.Layout;
import javafx.scene.text.Text;

public class Score extends Entity {

    static Text scoreText = new Text("Score: 0");

    public Score() {
        super(new Layout(10, 30, 1, 1, 0));
        getChildren().add(scoreText);
        updateScore();
    }

    public static void increaseScore() {
        StateMachine.getInstance().score++;
        updateScore();
    }

    public static void updateScore() {
        scoreText.setText("Score: " + StateMachine.getInstance().score);
    }
}
