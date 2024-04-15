package com.survivor.game;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.events.InputEvent;
import com.survivor.engine.listener.InputListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Gun extends Entity implements InputListener {

    Vector2D orientation = new Vector2D(0, 0);

    public Gun(Layout layout) {
        super(layout);

        attachInputListener();

        Text text = new Text("|5==");
        text.setX(getLayout().getWidth()/2);
        text.setY(getLayout().getHeight()/2);
        text.setFont(new Font(25));
        getChildren().add(text);
    }

    @Override
    public void inputUpdate(InputEvent event) {
        switch (event.type) {
            case MOUSE_JUST_PRESSED -> {
                System.out.println("Gun fired");
                new Bullet(new Layout(getX(), getY(), 15, 15, orientation.getAngle()), orientation.multiply(-1));
            }
        }
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);

        orientation = Vector2D.subtract(GameScene.getPlayer().getLayout().getCenter(), GameScene.getMousePosition()).scaleTo(1);
        setPosition(Vector2D.subtract(GameScene.getPlayer().getLayout().getPosition(), orientation.scaleTo(30)));

        setRotate(Math.atan2(orientation.getY(), orientation.getX()) / Math.PI * 180 + 180);
    }

    @Override
    public void gameUpdate(GameEvent event) {}
}
