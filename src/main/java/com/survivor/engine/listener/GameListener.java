package com.survivor.engine.listener;

import com.survivor.engine.events.GameEvent;
import com.survivor.engine.GameScene;

public interface GameListener {

    void gameUpdate(GameEvent event);

    default void attachGameListener() {
        GameScene.attachGameListener(this);
    };
}
