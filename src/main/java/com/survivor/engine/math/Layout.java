package com.survivor.engine.math;

public class Layout {
    private double x;
    private double y;

    private double width;
    private double height;

    private double rotation;

    public Layout(double x, double y, double width, double height, double rotation) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRotation() {
        return rotation;
    }

    public Vector2D getPosition() {
        return new Vector2D(x, y);
    }

    public Vector2D getCenter() {
        return new Vector2D(x + width / 2, y + height / 2);
    }

    public void update(double x, double y, double width, double height, double rotation) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setRotation(rotation);
    }
}
