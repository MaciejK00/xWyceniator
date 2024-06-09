package com.example.demo.common;

import java.util.List;

public class SurroundingsPriceFact {
    List<String> surroundingsList;
    Double price;

    public SurroundingsPriceFact(List<String> surroundingsList, Double price) {
        this.surroundingsList = surroundingsList;
        this.price = price;
    }

    public List<String> getSurroundingsList() {
        return surroundingsList;
    }

    public void setSurroundingsList(List<String> surroundingsList) {
        this.surroundingsList = surroundingsList;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
