package com.survivor.game.entities;

import com.survivor.engine.math.Vector2D;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class EndingScreen extends Parent {

    String c = "*";
    Text progress = new Text();
    int maxValue;
    int currentValue;

    public EndingScreen(Vector2D position, int maxValue, int currentValue) {
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        progress.setFont(new javafx.scene.text.Font(10));
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        progress.setText(c.repeat(maxValue));
        getChildren().add(progress);
    }

    public EndingScreen(Vector2D position, int maxValue, int currentValue, int size) {
        this(position, maxValue, 0);
        progress.setFont(new javafx.scene.text.Font(size));
    }

    public void updateProgress(int newValue) {
        currentValue = newValue;
        progress.setText(c.repeat(currentValue) + " ".repeat(maxValue - currentValue));
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setToFraction(double fraction) {
        updateProgress((int) (fraction * maxValue));
    }
}
