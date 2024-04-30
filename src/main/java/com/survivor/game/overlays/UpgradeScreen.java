package com.survivor.game.overlays;

import com.survivor.engine.GameScene;
import com.survivor.engine.entities.Menu;
import com.survivor.game.StateMachine;
import com.survivor.game.entities.Stats;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UpgradeScreen extends Menu {

    int numberOfUpgrades;
    Text upgradesLeft = new Text();

    public UpgradeScreen(double width, double height, int numberOfUpgrades) {
        super(width, height, "CHOOSE YOUR UPGRADE");
        this.numberOfUpgrades = numberOfUpgrades;
        Button damage = new Button("Bullet Damage");
        Button penetration = new Button("Bullet Penetration");
        Button bulletSpeed = new Button("Bullet Speed");
        Button bulletScatter = new Button("Bullet Scatter");
        Button speed = new Button("Movement Speed");
        Button maxHealth = new Button("Max Health");
        Button heal = new Button("Heal");
        Button reloadTime = new Button("Reload Time");


        upgradesLeft.setText("Upgrades left: " + numberOfUpgrades);
        upgradesLeft.setFont(new Font(20));
        upgradesLeft.setX(width / 2 - upgradesLeft.getBoundsInLocal().getWidth() / 2 );
        upgradesLeft.setY(height + upgradesLeft.getBoundsInLocal().getHeight());
        getChildren().add(upgradesLeft);

        damage.setOnAction(e -> {
            StateMachine.getInstance().damage += 10;
            pay();
            refreshAndClose();
        });
        penetration.setOnAction(e -> {
            StateMachine.getInstance().penetration += 1;
            pay();
            refreshAndClose();
        });
        bulletSpeed.setOnAction(e -> {
            StateMachine.getInstance().bulletSpeed += 50;
            pay();
            refreshAndClose();
        });
        bulletScatter.setOnAction(e -> {
            StateMachine.getInstance().bulletScatter = Math.max(0, StateMachine.getInstance().bulletScatter - 0.1);
            pay();
            refreshAndClose();
        });
        speed.setOnAction(e -> {
            StateMachine.getInstance().speed += 50;
            pay();
            refreshAndClose();
        });
        maxHealth.setOnAction(e -> {
            StateMachine.getInstance().maxHealth += 20;
            GameScene.getPlayer().increaseHealth(20);
            pay();
            refreshAndClose();
        });
        heal.setOnAction(e -> {
            GameScene.getPlayer().setHealth(StateMachine.getInstance().maxHealth);
            pay();
            refreshAndClose();
        });
        reloadTime.setOnAction(e -> {
            StateMachine.getInstance().reloadTime = Math.max(100, StateMachine.getInstance().reloadTime - 100);
            pay();
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

    private void pay() {
        numberOfUpgrades--;
        upgradesLeft.setText("Upgrades left: " + numberOfUpgrades);
    }

    private void refreshAndClose() {
        Stats.refresh();
        if (numberOfUpgrades <= 0) {
            close();
        }
    }

    @Override
    public void refresh() {
        GameScene.setOverlay(new UpgradeScreen(getWidth(), getHeight(), numberOfUpgrades));
    }
}
