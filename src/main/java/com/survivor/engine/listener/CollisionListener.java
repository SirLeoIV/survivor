package com.survivor.engine.listener;

import com.survivor.engine.GameScene;
import com.survivor.engine.events.CollisionEvent;

public interface CollisionListener {

    void collisionUpdate(CollisionEvent event);

    default void attachCollisionListener() {
        GameScene.attachCollisionListener(this);
    };
}
