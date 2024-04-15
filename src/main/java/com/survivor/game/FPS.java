package com.survivor.game;

import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.math.Layout;
import javafx.scene.text.Text;

import java.util.Arrays;

public class FPS extends Entity {


    Text fps = new Text("FPS: 0");

    int[] lastFrames = new int[100];
    int counter = 0;

    public FPS() {
        super(new Layout(10, 10, 1, 1, 0));
        getChildren().add(fps);
    }



    @Override
    public void process(long millisPassed) {
        try {
            int fps_int = (int) (1000 / millisPassed);
            lastFrames[counter % 100] = fps_int;
            fps.setText("FPS: " + (int) Arrays.stream(lastFrames).asDoubleStream().sum() / 100);
            counter++;
            if (counter == Integer.MAX_VALUE) counter = 0;
        } catch (ArithmeticException ignore) {}
    }

    @Override
    public void gameUpdate(GameEvent event) {}
}
