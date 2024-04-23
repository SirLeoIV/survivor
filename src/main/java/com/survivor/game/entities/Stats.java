package com.survivor.game.entities;

import com.survivor.engine.entities.Entity;
import com.survivor.engine.math.Layout;
import com.survivor.game.StateMachine;
import javafx.scene.text.Text;

public class Stats extends Entity {


    Text score = new Text();
    Text money = new Text();
    Text wave = new Text();

    Text maxHealth = new Text();
    Text damage = new Text();
    Text speed = new Text();
    Text penetration = new Text();

    static Stats instance = null;

    public Stats() {
        super(new Layout(10, 60, 1, 1, 0));
        score.setY(0);
        money.setY(20);
        wave.setY(40);
        maxHealth.setY(60);
        damage.setY(80);
        speed.setY(100);
        penetration.setY(120);
        // getChildren().add(score);
        getChildren().add(money);
        getChildren().add(wave);
        getChildren().add(maxHealth);
        getChildren().add(damage);
        getChildren().add(speed);
        getChildren().add(penetration);
        instance = this;
    }

    public static Stats getInstance() {
        if (instance == null) {
            instance = new Stats();
        }
        return instance;
    }

    public static void refresh() {
        StateMachine states = StateMachine.getInstance();
        getInstance().score.setText("Score: " + states.score);
        getInstance().money.setText("Money: " + states.money);
        getInstance().wave.setText("Wave: " + states.wave);
        getInstance().maxHealth.setText("Max Health: " + states.maxHealth);
        getInstance().damage.setText("Damage: " + states.damage);
        getInstance().speed.setText("Speed: " + states.speed);
        getInstance().penetration.setText("Penetration: " + states.penetration);
    }
}
