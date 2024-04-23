package com.survivor.game.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.events.InputEvent;
import com.survivor.engine.listener.InputListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class Gun extends Entity implements InputListener {

    Vector2D orientation = new Vector2D(0, 0);
    Text text = new Text("|5==");

    public Gun(Layout layout) {
        super(layout);

        attachInputListener();

        text.setFont(new Font(25));
        text.setX(0);
        text.setY(text.getBoundsInLocal().getHeight() / 3.9);

        getChildren().add(text);
    }

    @Override
    public void inputUpdate(InputEvent event) {
        switch (event.type) {
            case MOUSE_JUST_PRESSED -> {
                double angle = Math.atan2(orientation.getY(), orientation.getX()) - 135;
                double x = getX() + 40 * Math.cos(angle);
                double y = getY() + 40 * Math.sin(angle);
                new Bullet(new Layout(x, y, 15, 15, orientation.getAngle()), orientation.multiply(-1));
                // new Dot(new Layout(x, y, 5, 5, 0));
            }
        }
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);

        orientation = Vector2D.subtract(GameScene.getPlayer().getLayout().getCenter(), GameScene.getMousePosition()).scaleTo(1);

        setPosition(Vector2D.add(GameScene.getPlayer().getLayout().getPosition(), new Vector2D(30, 0)));

        double angle = Math.atan2(orientation.getY(), orientation.getX()) / Math.PI * 180 + 180;

        getTransforms().clear();
        getTransforms().add(new Rotate(angle, 0, 0));

        // new Dot(new Layout(getX(), getY(), 5, 5, 0));
        // new Dot(new Layout(getLayout().getCenter().getX(), getLayout().getCenter().getY(), 5, 5, 0));
    }

    @Override
    public void gameUpdate(GameEvent event) {}
}
