package com.survivor.engine.math;

public class Line2D {

    private Vector2D a;
    private Vector2D b;

    public Line2D(Vector2D a, Vector2D b) {
        this.a = a;
        this.b = b;
    }

    public Vector2D getA() {
        return a;
    }

    public Vector2D getB() {
        return b;
    }

    public double getLength() {
        return Vector2D.subtract(a, b).getMagnitude();
    }

    public boolean intersects(Line2D line) {
        Vector2D p = a;
        Vector2D r = Vector2D.subtract(b, a);
        Vector2D q = line.getA();
        Vector2D s = Vector2D.subtract(line.getB(), line.getA());

        double t = Vector2D.crossProduct(Vector2D.subtract(q, p), s) / Vector2D.crossProduct(r, s);
        double u = Vector2D.crossProduct(Vector2D.subtract(q, p), r) / Vector2D.crossProduct(r, s);

        return t >= 0 && t <= 1 && u >= 0 && u <= 1;
    }
}
