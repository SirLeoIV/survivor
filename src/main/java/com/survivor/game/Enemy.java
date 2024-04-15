package com.survivor.game;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Character;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.CollisionEvent;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Enemy extends Character implements CollisionListener {

    public Enemy(Layout layout) {
        super(layout);

        attachCollisionListener();

        Text text = new Text("E");
        text.setX(0);
        text.setY(getLayout().getHeight());
        text.setFont(new Font(50));
        getChildren().add(text);

        speed = 200;
        System.out.println("Enemy created");
    }


    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);

        Vector2D playerPosition = GameScene.getPlayer().getLayout().getCenter();
        direction = Vector2D.subtract(playerPosition, getLayout().getCenter());

        if (Vector2D.subtract(playerPosition, getLayout().getCenter()).getMagnitude() < 20) {
            direction = new Vector2D(0, 0);
        }

        checkCollisions();
    }

    @Override
    public void collisionUpdate(CollisionEvent event) {
        if (event.entity1 == this || event.entity2 == this) {
            if (event.entity1 instanceof Bullet || event.entity2 instanceof Bullet) {
                Score.increaseScore();
                setX(Math.random() * getScene().getWidth());
                setY(Math.random() * getScene().getHeight());

                new Enemy(new Layout(Math.random() * getScene().getWidth(), Math.random() * getScene().getHeight(), 27, 35, 0));
            }
        }
    }

    private void checkCollisions() {
        ArrayList<Entity> entities = new ArrayList<>(GameScene.getEntities());
        for (Entity entity : entities) {
            if (entity != this && isColliding(entity)) {
                GameScene.notifyCollisionListeners(
                        new CollisionEvent("Collision between: ", this, entity));
            }
        }
    }
}
