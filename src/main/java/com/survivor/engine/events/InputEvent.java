package com.survivor.engine.events;

public class InputEvent extends Event {

    public InputKey key;
    public InputType type;

    public InputEvent(String name, InputType type, InputKey key) {
        super(name, type);
        this.type = type;
        this.key = key;
    }

    @Override
    public String toString() {
        return "INPUT_EVENT: " + type + " " + key;
    }
}
