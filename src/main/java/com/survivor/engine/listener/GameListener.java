package com.survivor.engine.listener;

import com.survivor.engine.GameScene;
import com.survivor.engine.events.GameEvent;

public interface GameListener {

    default void gameUpdate(GameEvent event) {};

    default void attachGameListener() {
        GameScene.attachGameListener(this);
    };
}
