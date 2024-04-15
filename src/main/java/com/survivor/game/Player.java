package com.survivor.game;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Character;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.events.InputEvent;
import com.survivor.engine.listener.InputListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player extends Character implements InputListener {

    double dashStart = 0;
    Vector2D dashDirection = new Vector2D(0, 0);
    boolean dashing = false;
    Text text;

    public Player(Layout layout) {
        super(layout);

        attachInputListener();
        GameScene.setPlayer(this);


        text = new Text("P");
        text.setX(0);
        System.out.println(getLayout().getHeight());
        text.setY(getLayout().getHeight());
        text.setFont(new Font(50));
        getChildren().add(text);

        System.out.println("Player created");
    }

    @Override
    public void gameUpdate(GameEvent event) {
        System.out.println(event);
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
            move(dashDirection.getX() * speed * 6 * millisPassed / 1000
                    , dashDirection.getY() * speed * 6 * millisPassed / 1000);
            dashing = System.currentTimeMillis() - dashStart < 100;
        }
    }
}
