package com.survivor.engine.events;

public abstract class Event {

    protected String name;
    protected Object data;

    public Event(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "EVENT: " + name + " " + data.toString();
    }
}
