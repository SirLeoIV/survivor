package com.survivor.engine.events;

public class GameEvent extends Event {

    GameEventType type;

    public GameEvent(GameEventType type, Object data) {
        super(type.name(), data);
        this.type = type;
    }

    public GameEventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "GAME_EVENT: " + name + " " + data.toString();
    }
}
