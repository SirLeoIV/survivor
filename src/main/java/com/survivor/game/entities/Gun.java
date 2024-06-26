package com.survivor.game.entities;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Entity;
import com.survivor.engine.events.GameEvent;
import com.survivor.engine.events.InputEvent;
import com.survivor.engine.listener.InputListener;
import com.survivor.engine.math.Layout;
import com.survivor.engine.math.Vector2D;
import com.survivor.game.ProgressBar;
import com.survivor.game.StateMachine;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class Gun extends Entity implements InputListener {

    Vector2D orientation = new Vector2D(0, 0);
    Text text = new Text("|5==");
    ProgressBar bulletsLeft = new ProgressBar(new Vector2D(0, 0), 10, 10);

    long nextReload = 1000;
    boolean firedSinceReload = false;

    Dot dot1 = new Dot(new Layout(0, 0, 5, 5, 0));

    public Gun(Layout layout) {
        super(layout);

        attachInputListener();

        text.setFont(new Font(25));
        text.setX(0);
        text.setY(text.getBoundsInLocal().getHeight() / 3.9);

        bulletsLeft.setTranslateX(15);
        bulletsLeft.setTranslateY(-5);

        getChildren().add(text);
        getChildren().add(bulletsLeft);
    }

    @Override
    public void inputUpdate(InputEvent event) {
        if (!GameScene.isGameLoopRunning()) return;
        switch (event.type) {
            case MOUSE_JUST_PRESSED -> {
                if (bulletsLeft.getCurrentValue() <= 0) return;
                firedSinceReload = true;
                Vector2D bulletOrientation = orientation.scatter(StateMachine.getInstance().bulletScatter);
                double angle = Math.atan2(orientation.getY(), orientation.getX()) - Math.toRadians(180);
                double x = getPosition().getX() + 50 * Math.cos(angle);
                double y = getPosition().getY() + 50 * Math.sin(angle);
                dot1.setPosition(new Vector2D(x, y));
                new Bullet(new Layout(x, y, 15, 15, bulletOrientation.getAngle()), bulletOrientation.multiply(-1));
                bulletsLeft.updateProgress(bulletsLeft.getCurrentValue() - 1);
            }
        }
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);

        if (bulletsLeft.getCurrentValue() < 10) {
            nextReload -= millisPassed;
            if (nextReload <= 0) {
                bulletsLeft.updateProgress(bulletsLeft.getCurrentValue()+1);
                nextReload = (firedSinceReload) ? StateMachine.getInstance().reloadTime : StateMachine.getInstance().reloadTime/2;
                firedSinceReload = false;
            }
        }

        setPosition(Vector2D.add(GameScene.getPlayer().getLayout().getPosition(), new Vector2D(30, 0)));

        orientation = Vector2D.subtract(getLayout().getPosition(), GameScene.getMousePosition()).scaleTo(1);

        double angle = Math.atan2(orientation.getY(), orientation.getX()) - Math.toRadians(180);

        getTransforms().clear();
        getTransforms().add(new Rotate(Math.toDegrees(angle), 0, 0));

        double x = getPosition().getX() + 50 * Math.cos(angle);
        double y = getPosition().getY() + 50 * Math.sin(angle);
        dot1.setPosition(new Vector2D(x, y));



        // new Dot(new Layout(getX(), getY(), 5, 5, 0));
        // new Dot(new Layout(getLayout().getCenter().getX(), getLayout().getCenter().getY(), 5, 5, 0));
    }

    @Override
    public void gameUpdate(GameEvent event) {}
}
