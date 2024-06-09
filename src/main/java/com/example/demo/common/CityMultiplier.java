package com.example.demo.common;

import lombok.Getter;
import lombok.Setter;


public class CityMultiplier {
    String city;
    int multiplier;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public CityMultiplier(String city, int multiplier) {
        this.city = city;
        this.multiplier = multiplier;
    }
}