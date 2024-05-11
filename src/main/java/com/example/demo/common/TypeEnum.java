package com.example.demo.common;

public enum TypeEnum {
    AGRICULTURAL("Rolna"),
    BUILDING("Budowlana"),
    FOREST("Leśna"),
    LEISURE("Rekreacyjna"),
    INVESTMENT("Inwestycyjna"),
    HABITAT("Siedliskowa");

    final String name;

    TypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}