package com.example.demo.common;

public enum CityEnum {
    BIALYSTOK("Białystok"),
    POZNAN("Poznań"),
    WROCLAW("Wrocław"),
    WARSAW("Warszawa");

    final String name;

    CityEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}