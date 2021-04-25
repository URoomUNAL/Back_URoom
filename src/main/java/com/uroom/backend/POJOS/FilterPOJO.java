package com.uroom.backend.POJOS;

import java.util.Set;

public class FilterPOJO {

    private double min_score;
    private DistancePOJO distance;
    private PricePOJO price;
    private Set<String> rules;
    private Set<String> services;

    public FilterPOJO(double min_score, DistancePOJO distance, PricePOJO price, Set<String> rules, Set<String> services) {
        this.min_score = min_score;
        this.distance = distance;
        this.price = price;
        this.rules = rules;
        this.services = services;
    }

    public double getMin_score() {
        return min_score;
    }

    public void setMin_score(double min_score) {
        this.min_score = min_score;
    }

    public DistancePOJO getDistance() {
        return distance;
    }

    public void setDistance(DistancePOJO distance) {
        this.distance = distance;
    }

    public PricePOJO getPrice() {
        return price;
    }

    public void setPrice(PricePOJO price) {
        this.price = price;
    }

    public Set<String> getRules() {
        return rules;
    }

    public void setRules(Set<String> rules) {
        this.rules = rules;
    }

    public Set<String> getServices() {
        return services;
    }

    public void setServices(Set<String> services) {
        this.services = services;
    }
}
