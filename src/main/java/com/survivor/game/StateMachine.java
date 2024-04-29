package com.survivor.game;

public class StateMachine {

    public int score = 0;
    public int money = 0;
    public int wave = 1;

    public int maxHealth = 100;
    public int damage = 30;
    public int speed = 300;
    public int penetration = 1;
    public int bulletSpeed = 500;
    public double bulletScatter = 0.5;
    public long reloadTime = 1000;

    static StateMachine instance = null;

    public static StateMachine getInstance() {
        if (instance == null) {
            instance = new StateMachine();
        }
        return instance;
    }

    public static StateMachine resetInstance() {
        instance = new StateMachine();
        return instance;
    }
}
