package com.survivor.engine.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.math.Layout;
import javafx.scene.text.Text;

public class Banner extends Entity {

    Text text = new Text();
    long duration;
    long fadeDuration;

    public Banner(String text, long duration) {
        super(new Layout(
                GameScene.getInstance().getScene().getWidth() / 2,
                GameScene.getInstance().getScene().getHeight()/2, 0, 0, 0));
        this.text.setText(text);
        this.text.setFont(new javafx.scene.text.Font(70));
        setTranslateX(GameScene.getInstance().getScene().getWidth() / 2 - this.text.getLayoutBounds().getWidth() / 2);
        setTranslateY(GameScene.getInstance().getScene().getHeight() / 2 - this.text.getLayoutBounds().getHeight() / 2);
        getChildren().add(this.text);
        this.duration = duration;
        this.fadeDuration = Math.max(duration / 4, 500);
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);
        duration -= millisPassed;
        if (duration < fadeDuration) {
            text.setOpacity(duration / (double) fadeDuration);
        }
        if (duration <= 0) {
            GameScene.removeEntity(this);
        }
    }

}
