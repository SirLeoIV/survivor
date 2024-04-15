package com.survivor.engine.events;

import com.survivor.engine.entities.Entity;

import java.util.List;

public class CollisionEvent extends Event {

    public final Entity entity1;
    public final Entity entity2;

    public CollisionEvent(String name, Entity entity1, Entity entity2) {
        super(name, List.of(entity1, entity2));
        this.entity1 = entity1;
        this.entity2 = entity2;
    }

    @Override
    public String toString() {
        return "COLLISION_EVENT: " + name + " " + entity1 + " : " + entity2;
    }
}
