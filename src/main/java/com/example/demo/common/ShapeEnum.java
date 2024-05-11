package com.example.demo.common;

public enum ShapeEnum {
    REGULAR("Regularna"),
    IRREGULAR("Nieregularna");

    final String name;

    ShapeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}