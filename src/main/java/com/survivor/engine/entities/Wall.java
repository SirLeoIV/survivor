package com.survivor.engine.entities;

import com.survivor.engine.math.Layout;

public abstract class Wall extends Entity {

    public Wall(Layout layout) {
        super(layout);
    }

    @Override
    public void process(long millisPassed) {
        super.process(millisPassed);
    }

}
