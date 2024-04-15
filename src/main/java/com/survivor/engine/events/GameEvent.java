package com.survivor.engine.events;

public class GameEvent extends Event {

    public GameEvent(String name, Object data) {
        super(name, data);
    }

    @Override
    public String toString() {
        return "GAME_EVENT: " + name + " " + data.toString();
    }
}
