package com.survivor.game.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Character;
import com.survivor.engine.events.CollisionEvent;
import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import com.survivor.game.ProgressBar;
import com.survivor.game.Score;
import com.survivor.game.StateMachine;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Enemy extends Character implements CollisionListener {

    private static final ArrayList<Enemy> enemies = new ArrayList<>();

    private final int maxHealth = 100;
    private int health = maxHealth;
    private long lastHitTime = 0;

    private ProgressBar healthBar = new ProgressBar(new Vector2D(0, 0), 8, 8);

    public Enemy(Vector2D position) {
        super(new Layout(position.getX(), position.getY(), 27, 35, 0));

        attachCollisionListener();

        Text text = new Text("E");
        text.setX(0);
        text.setY(getLayout().getHeight());
        text.setFont(new Font(50));
        getChildren().add(text);
        getChildren().add(healthBar);
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
                lastHitTime = System.currentTimeMillis();
                health = health - StateMachine.getInstance().damage;
                if (health <= 0) {
                    Score.increaseScore();
                    StateMachine.getInstance().money++;
                    GameScene.removeEntity(this);
                    enemies.remove(this);
                    return;
                }
                healthBar.setToFraction((double) health / maxHealth);
            }
        }
    }

    private void checkCollisions() {
        if (lastHitTime < System.currentTimeMillis() - 50) {
            for (Bullet bullet : Bullet.getAllBullets()) {
                if (isCollidingV2(bullet)) {
                    GameScene.notifyCollisionListeners(
                            new CollisionEvent("Collision between: ", this, bullet));
                }
            }
        }
    }

    @Override
    protected double getSpeed() {
        return 200;
    }

    public static ArrayList<Enemy> getAllEnemies() {
        return GameScene.getEntitiesByClass(Enemy.class).stream()
                .map(entity -> (Enemy) entity)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
