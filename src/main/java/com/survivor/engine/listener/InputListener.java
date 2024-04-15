package com.survivor.engine.listener;

import com.survivor.engine.GameScene;
import com.survivor.engine.events.InputEvent;

public interface InputListener {

    void inputUpdate(InputEvent event);

    default void attachInputListener() {
        GameScene.attachInputListener(this);
    };
}
