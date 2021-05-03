package com.uroom.backend.Models.POJOS;

public class PricePOJO {
    private int min;
    private int max;

    public PricePOJO(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
