package com.example.demo.common;

public enum MediaEnum {
    POWER("Prąd"),
    GAS("Gaz"),
    WATER("Woda"),
    SEWER("Kanalizacja");

    final String name;

    MediaEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}