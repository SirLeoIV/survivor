package com.survivor.game.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Character;
import com.survivor.engine.events.*;
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

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private boolean upAfterDown = false;
    private boolean rightAfterLeft = false;

    public Player(Layout layout) {
        super(layout);

        attachInputListener();
        attachCollisionListener();
        GameScene.setPlayer(this);


        text = new Text("P");
        text.setX(0);
        text.setY(getLayout().getHeight());
        text.setFont(new Font(50));
        getChildren().add(text);
        getChildren().add(healthBar);

        System.out.println("Player created");
    }

    @Override
    public void gameUpdate(GameEvent event) {
        if (event.getType() == GameEventType.GAME_RESUME) {
            System.out.println("Player resumed");
            upPressed = false;
            downPressed = false;
            leftPressed = false;
            rightPressed = false;
            updateDirection();
        }
    }

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
                    case KEY_UP, KEY_W -> upPressed();
                    case KEY_DOWN, KEY_S -> downPressed();
                    case KEY_LEFT, KEY_A -> leftPressed();
                    case KEY_RIGHT, KEY_D -> rightPressed();
                    case KEY_SPACE -> dash();
                }}
            case KEY_PRESSED -> {
                switch (event.key) {
                    case KEY_UP, KEY_W -> upPressing();
                    case KEY_DOWN, KEY_S -> downPressing();
                    case KEY_LEFT, KEY_A -> leftPressing();
                    case KEY_RIGHT, KEY_D -> rightPressing();
                }}
            case KEY_RELEASED -> {
                switch (event.key) {
                    case KEY_UP, KEY_W -> upPressed = false;
                    case KEY_DOWN, KEY_S -> downPressed = false;
                    case KEY_LEFT, KEY_A -> leftPressed = false;
                    case KEY_RIGHT, KEY_D -> rightPressed = false;
                }}
        }
        if (event.type == InputType.KEY_JUST_PRESSED || event.type == InputType.KEY_RELEASED) updateDirection();
    }

    protected void upPressed() {
        upPressed = true;
        upAfterDown = true;
    }

    protected void upPressing() {
        upPressed = true;
        if (!downPressed) upAfterDown = true;
    }

    protected void downPressed() {
        downPressed = true;
        upAfterDown = false;
    }

    protected void downPressing() {
        downPressed = true;
        if (!upPressed) upAfterDown = false;
    }

    protected void leftPressed() {
        leftPressed = true;
        rightAfterLeft = false;
    }

    protected void leftPressing() {
        leftPressed = true;
        if (!rightPressed) rightAfterLeft = false;
    }

    protected void rightPressed() {
        rightPressed = true;
        rightAfterLeft = true;
    }

    protected void rightPressing() {
        rightPressed = true;
        if (!leftPressed) rightAfterLeft = true;
    }

    protected void updateDirection() {
        direction.setY(0);
        if (upPressed && !downPressed) direction.setY(-1);
        else if (downPressed && !upPressed) direction.setY(1);
        else if (upPressed && downPressed) direction.setY(upAfterDown ? -1 : 1);

        direction.setX(0);
        if (leftPressed && !rightPressed) direction.setX(-1);
        else if (rightPressed && !leftPressed) direction.setX(1);
        else if (leftPressed && rightPressed) direction.setX(rightAfterLeft ? 1 : -1);
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
                updateHealthBar();
            }
        }
    }

    private void updateHealthBar() {
        healthBar.setToFraction((double) health / StateMachine.getInstance().maxHealth);
        Stats.refresh();
    }

    @Override
    public double getSpeed() {
        return StateMachine.getInstance().speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(StateMachine.getInstance().maxHealth, Math.max(0, health));
        updateHealthBar();
    }

    public void increaseHealth(int health) {
        setHealth(this.health + health);
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
