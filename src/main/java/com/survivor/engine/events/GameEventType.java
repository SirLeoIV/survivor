package com.survivor.engine.events;

public enum GameEventType {
    GAME_START,
    GAME_PAUSE,
    GAME_RESUME,
    GAME_OVER,

    ATTACH_ENTITY,
    REMOVE_ENTITY,

    ATTACH_LISTENER,
    DETACH_LISTENER,
}
