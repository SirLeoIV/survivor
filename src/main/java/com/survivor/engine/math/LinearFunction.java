package com.survivor.engine.math;

public class LinearFunction {

    private double a; // x frames per second
    private double b; // y frames per second

    private double x1;
    private double x2;

    public LinearFunction(double a, double b, double x1, double x2) {
        this.a = a;
        this.b = b;
    }

    public LinearFunction(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public LinearFunction(Vector2D point1, Vector2D point2) {
        this.a = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
        this.b = point1.getY() - a * point1.getX();

        this.x1 = Math.min(point1.getX(), point2.getX());
        this.x2 = Math.max(point1.getX(), point2.getX());
    }

    public double getY(double x) {
        return a * x + b;
    }

    public static boolean isIntersecting(LinearFunction f1, LinearFunction f2) {
        if (f1.x2 < f2.x1 || f2.x2 < f1.x1) return false;
        double x1 = Math.max(f1.x1, f2.x1);
        double x2 = Math.min(f1.x2, f2.x2);
        return (f1.getY(x1) - f2.getY(x1)) * (f1.getY(x2) - f2.getY(x2)) <= 0;
    }

    @Override
    public String toString() {
        return "LinearFunction{" +
                "a=" + a +
                ", b=" + b +
                ", x1=" + x1 +
                ", x2=" + x2 + "}";
    }
}
