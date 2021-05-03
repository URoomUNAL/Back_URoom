package com.uroom.backend.Models.POJOS;

import java.util.List;

public class DistancePOJO {
    private List<Double> origin;
    private double radius;

    public DistancePOJO(List<Double> origin, double radius) {
        this.origin = origin;
        this.radius = radius;
    }

    public List<Double> getOrigin() {
        return origin;
    }

    public void setOrigin(List<Double> origin) {
        this.origin = origin;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
