package com.survivor.game.overlays;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Menu;
import com.survivor.game.StateMachine;
import com.survivor.game.entities.Stats;
import javafx.scene.control.Button;

public class UpgradeScreen extends Menu {

    public UpgradeScreen(double width, double height) {
        super(width, height, "CHOOSE YOUR UPGRADE");
        Button damage = new Button("Bullet Damage");
        Button penetration = new Button("Bullet Penetration");
        Button bulletSpeed = new Button("Bullet Speed");
        Button bulletScatter = new Button("Bullet Scatter");
        Button speed = new Button("Movement Speed");
        Button maxHealth = new Button("Max Health");
        Button heal = new Button("Heal");
        Button reloadTime = new Button("Reload Time");

        damage.setOnAction(e -> {
            StateMachine.getInstance().damage += 10;
            pay(5);
            refreshAndClose();
        });
        penetration.setOnAction(e -> {
            StateMachine.getInstance().penetration += 1;
            pay(5);
            refreshAndClose();
        });
        bulletSpeed.setOnAction(e -> {
            StateMachine.getInstance().bulletSpeed += 50;
            pay(5);
            refreshAndClose();
        });
        bulletScatter.setOnAction(e -> {
            StateMachine.getInstance().bulletScatter = Math.max(0, StateMachine.getInstance().bulletScatter - 0.1);
            pay(5);
            refreshAndClose();
        });
        speed.setOnAction(e -> {
            StateMachine.getInstance().speed += 50;
            pay(5);
            refreshAndClose();
        });
        maxHealth.setOnAction(e -> {
            StateMachine.getInstance().maxHealth += 20;
            GameScene.getPlayer().increaseHealth(20);
            pay(5);
            refreshAndClose();
        });
        heal.setOnAction(e -> {
            GameScene.getPlayer().setHealth(StateMachine.getInstance().maxHealth);
            pay(5);
            refreshAndClose();
        });
        reloadTime.setOnAction(e -> {
            StateMachine.getInstance().reloadTime = Math.max(100, StateMachine.getInstance().reloadTime - 100);
            pay(5);
            refreshAndClose();
        });

        addButton(damage, 1);
        addButton(penetration, 2);
        addButton(bulletSpeed, 3);
        addButton(bulletScatter, 4);
        addButton(speed, 5);
        addButton(maxHealth, 6);
        addButton(heal, 7);
        addButton(reloadTime, 8);
    }

    private void pay(int amount) {
        StateMachine.getInstance().money -= amount;
    }

    private void refreshAndClose() {
        Stats.refresh();
        close();
    }
}
