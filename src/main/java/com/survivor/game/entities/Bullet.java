package com.survivor.game.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.CollisionEvent;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import com.survivor.game.StateMachine;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Bullet extends Entity implements CollisionListener {

    Vector2D orientation;
    double speed;
    long lifeTimeLeft = 2000;

    public int penetrationLeft = StateMachine.getInstance().penetration;

    public Bullet(Layout layout, Vector2D orientation) {
        super(layout);
        this.orientation = orientation;
        attachCollisionListener();

        speed = StateMachine.getInstance().bulletSpeed;

        setY(getY() - getLayout().getHeight() / 2);
        setX(getX() - getLayout().getWidth() / 2);

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
        new Dot(new Layout(getLayout().getCenter().getX(), getLayout().getCenter().getY(), 1, 1, 0), 500);
        lifeTimeLeft -= millisPassed;
        if (lifeTimeLeft <= 0) {
            GameScene.removeEntity(this);
        }
    }

    @Override
    public void gameUpdate(GameEvent event) {}

    @Override
    public void collisionUpdate(CollisionEvent event) {
        if (event.entity1 == this || event.entity2 == this) {
            penetrationLeft--;
            if (penetrationLeft <= 0) {
                GameScene.removeEntity(this);
            }
        }
    }

    public static ArrayList<Bullet> getAllBullets() {
        return GameScene.getEntitiesByClass(Bullet.class).stream()
                .map(entity -> (Bullet) entity)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
