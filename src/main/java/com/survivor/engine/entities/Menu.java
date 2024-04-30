package com.survivor.engine.entities;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Menu extends Overlay {

    Text title = new Text();
    ArrayList<ArrayList<Button>> buttons = new ArrayList<>();

    AnimationTimer timer;
    long initTime = System.currentTimeMillis();

    public Menu(double width, double height, String titleString) {
        super(width, height);
        getChildren().add(title);
        title.setFont(new javafx.scene.text.Font(20));
        setTitle(titleString);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - initTime > 500) {
                    for (ArrayList<Button> buttonRow : buttons) {
                        for (Button button : buttonRow) {
                            button.setDisable(false);
                        }
                    }
                    timer.stop();
                }
            }
        };
        timer.start();
    }

    public void addButton(Button button, int row) {
        button.setDisable(true);
        if (buttons.size() <= row) {
            buttons.add(new ArrayList<>());
        }
        buttons.get(row-1).add(button);
        for (Button b : buttons.get(row-1)) {
            b.setPrefWidth(getWidth() / buttons.get(row-1).size() - 20);
            b.setPrefHeight(50);
        }
        getChildren().add(button);
        repaint();
    }

    public void setTitle(String title) {
        this.title.setText(title);
        this.title.setX(getWidth() / 2 - this.title.getLayoutBounds().getWidth() / 2);
        this.title.setY(this.title.getLayoutBounds().getHeight());
    }

    public void repaint() {
        setTitle(title.getText());
        double menuHeight = (getHeight() - title.getLayoutBounds().getHeight());
        for (int i = 0; i < buttons.size(); i++) {
            for (int j = 0; j < buttons.get(i).size(); j++) {
                Button button = buttons.get(i).get(j);
                button.setLayoutX(getWidth() / buttons.get(i).size() * j + 10);
                button.setLayoutY(
                        title.getLayoutBounds().getHeight()
                                + (menuHeight) / buttons.size() * i
                                + (menuHeight) / buttons.size() / 2
                                - button.getPrefHeight() / 2);
            }
        }
    }

    public void close() {
        remove();
    }

    @Override
    public void refresh() {
        repaint();
    }
}
