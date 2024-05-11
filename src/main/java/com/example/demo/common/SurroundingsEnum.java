package com.example.demo.common;

public enum SurroundingsEnum {
    EXPRESS("Droga ekspresowa w okolicy"),
    HIGHWAY("Autostrada w okolicy"),
    TARMAC("Dojazd drogą asfaltowa");
    final String name;

    SurroundingsEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}