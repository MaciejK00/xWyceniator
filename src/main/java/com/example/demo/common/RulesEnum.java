package com.example.demo.common;

public enum RulesEnum {
    MEDIA("SuggestMediaPrice.drl"),
    SIZE("SuggestSizePrice.drl"),
    TYPE("SuggestTypePrice.drl"),
    CITY("SuggestCityPrice.drl"),
    SHAPE("SuggestShapePrice.drl"),
    SURROUNDINGS("SuggestSurroundingsPrice.drl");

    final String fileName;

    public String getFileName() {
        return fileName;
    }

    RulesEnum(String fileName) {
        this.fileName = fileName;
    }
}