package com.survivor.game.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Character;
import com.survivor.engine.events.CollisionEvent;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.events.InputEvent;
import com.survivor.engine.listener.CollisionListener;
import com.survivor.engine.listener.InputListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import com.survivor.game.ProgressBar;
import com.survivor.game.StateMachine;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player extends Character implements InputListener, CollisionListener {

    double dashStart = 0;
    Vector2D dashDirection = new Vector2D(0, 0);
    boolean dashing = false;
    Text text;

    private int health = StateMachine.getInstance().maxHealth;
    private long lastHitTime = 0;

    private ProgressBar healthBar = new ProgressBar(new Vector2D(0, 0), 8, 8);

    public Player(Layout layout) {
        super(layout);

        attachInputListener();
        attachCollisionListener();
        GameScene.setPlayer(this);


        text = new Text("P");
        text.setX(0);
        System.out.println(getLayout().getHeight());
        text.setY(getLayout().getHeight());
        text.setFont(new Font(50));
        getChildren().add(text);
        getChildren().add(healthBar);

        System.out.println("Player created");
    }

    @Override
    public void gameUpdate(GameEvent event) {}

    private void dash() {
        if (System.currentTimeMillis() - dashStart < 1000 && direction.getMagnitude() == 0) return;
        dashStart = System.currentTimeMillis();
        dashDirection = direction;
        dashing = true;
    }

    @Override
    public void inputUpdate(InputEvent event) {
        switch (event.type) {
            case KEY_JUST_PRESSED -> {
                switch (event.key) {
                    case KEY_UP, KEY_W -> moveUp();
                    case KEY_DOWN, KEY_S -> moveDown();
                    case KEY_LEFT, KEY_A -> moveLeft();
                    case KEY_RIGHT, KEY_D -> moveRight();
                    case KEY_SPACE -> dash();
                }}
            case KEY_RELEASED -> {
                switch (event.key) {
                    case KEY_UP, KEY_W -> direction.setY(direction.getY() == -1 ? 0 : direction.getY());
                    case KEY_DOWN, KEY_S -> direction.setY(direction.getY() == 1 ? 0 : direction.getY());
                    case KEY_LEFT, KEY_A -> direction.setX(direction.getX() == -1 ? 0 : direction.getX());
                    case KEY_RIGHT, KEY_D -> direction.setX(direction.getX() == 1 ? 0 : direction.getX());
                }}

        }
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);

        if (dashing) {
            move(dashDirection.getX() * StateMachine.getInstance().speed * 6 * millisPassed / 1000
                    , dashDirection.getY() * StateMachine.getInstance().speed * 6 * millisPassed / 1000);
            dashing = System.currentTimeMillis() - dashStart < 100;
        }
        checkCollisions();
    }


    @Override
    public void collisionUpdate(CollisionEvent event) {
        if (event.entity1 == this || event.entity2 == this) {
            if (event.entity1 instanceof Enemy || event.entity2 instanceof Enemy) {
                lastHitTime = System.currentTimeMillis();
                health = health - 10;
                if (health < 0) health = 0;
                healthBar.setToFraction((double) health / StateMachine.getInstance().maxHealth);
                System.out.println("Player health: " + health);
            }
        }
    }

    @Override
    public double getSpeed() {
        return StateMachine.getInstance().speed;
    }

    public int getHealth() {
        return health;
    }

    private void checkCollisions() {
        if (dashing || lastHitTime > System.currentTimeMillis() - 400) return;
        for (Enemy enemy : Enemy.getAllEnemies()) {
            if (isCollidingV2(enemy)) {
                GameScene.notifyCollisionListeners(
                        new CollisionEvent("Collision between: ", this, enemy));
            }
        }
    }
}
