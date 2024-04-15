package com.survivor.game;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.CollisionEvent;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.events.InputEvent;
import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.listener.InputListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Bullet extends Entity implements CollisionListener {

    Vector2D orientation;
    double speed = 500;
    long creationTime = System.currentTimeMillis();
    long lifeTime = 2000;

    public Bullet(Layout layout, Vector2D orientation) {
        super(layout);
        this.orientation = orientation;
        attachCollisionListener();

        Text text = new Text(">");
        text.setX(0);
        text.setY(getLayout().getHeight());
        text.setFont(new Font(20));
        getChildren().add(text);
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);


        Vector2D velocity = orientation.scaleTo(speed);
        setPosition(Vector2D.add(getPosition(), velocity.multiply((double) millisPassed / 1000)));

        if (System.currentTimeMillis() - creationTime > lifeTime) {
            GameScene.removeEntity(this);
        }
    }

    @Override
    public void gameUpdate(GameEvent event) {}

    @Override
    public void collisionUpdate(CollisionEvent event) {
        if (event.entity1 == this || event.entity2 == this) {
            // GameScene.removeEntity(this);
        }
    }
}
