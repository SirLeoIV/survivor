package com.survivor.game.overlays;

import com.survivor.engine.entities.Menu;
import com.survivor.game.StateMachine;
import com.survivor.game.entities.Stats;
import javafx.scene.control.Button;

public class UpgradeScreen extends Menu {

    public UpgradeScreen(double width, double height) {
        super(width, height, "CHOOSE YOUR UPGRADE");
        Button damage = new Button("Bullet Damage");
        Button penetration = new Button("Bullet Penetration");
        Button speed = new Button("Movement Speed");
        damage.setOnAction(e -> {
            StateMachine.getInstance().damage += 10;
            StateMachine.getInstance().money -= 5;
            Stats.refresh();
            close();
        });
        penetration.setOnAction(e -> {
            StateMachine.getInstance().penetration += 1;
            StateMachine.getInstance().money -= 5;
            Stats.refresh();
            close();
        });
        speed.setOnAction(e -> {
            StateMachine.getInstance().speed += 50;
            StateMachine.getInstance().money -= 5;
            Stats.refresh();
            close();
        });
        addButton(damage, 1);
        addButton(penetration, 2);
        addButton(speed, 3);
    }
}
