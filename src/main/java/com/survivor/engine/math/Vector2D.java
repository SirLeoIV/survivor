package com.survivor.engine.math;

public class Vector2D {

    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void update(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public boolean cutToMagnitude(double magnitude) {
        double currentMagnitude = getMagnitude();
        if (currentMagnitude > magnitude) {
            x = x / currentMagnitude * magnitude;
            y = y / currentMagnitude * magnitude;
            if (x < 0.1 && x > -0.1)
                x = 0;
            if (y < 0.1 && y > -0.1)
                y = 0;
            return true;
        }
        return false;
    }

    public Vector2D scaleTo(double magnitude) {
        double currentMagnitude = getMagnitude();
        return new Vector2D(x / currentMagnitude * magnitude, y / currentMagnitude * magnitude);
    }

    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    public static double getAngle(Vector2D vector) {
        return (Math.atan2(vector.getY(), vector.getX()) / Math.PI * 180 + 180);
    }

    public double getAngle() {
        return Math.atan2(y, x) / Math.PI * 180 + 180;
    }

    public static Vector2D multiply(Vector2D vector, double scalar) {
        return new Vector2D(vector.getX() * scalar, vector.getY() * scalar);
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public Vector2D absolute() {
        return new Vector2D(Math.abs(x), Math.abs(y));
    }

    public Vector2D scatter(double max) {
        return new Vector2D(x + Math.random() * max - max / 2, y + Math.random() * max - max / 2);
    }

    public static void main(String[] args) {
        Vector2D v = new Vector2D(1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(v.scatter(0.5));
        }
        System.out.println("----");
        System.out.println(Math.toDegrees(Math.atan2(1, 1)));
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
