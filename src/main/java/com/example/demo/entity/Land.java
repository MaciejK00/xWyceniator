package com.example.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Land {
    private String city;
    private List<String> media = new ArrayList<>();
    private String type;
    private Long size;
    private boolean regular;
    private List<String> surroundings = new ArrayList<>();
    private Map<String, String> cityMap = new HashMap<>();
    private Map<String, String> mediaMap = new HashMap<>();
    private Map<String, String> typeMap = new HashMap<>();
    private Double shapeMultiplier;


    public Double getSizePrice() {
        return sizePrice;
    }

    public void setSizePrice(Double sizePrice) {
        this.sizePrice = sizePrice;
    }

    private Double sizePrice;
    private Map<String, String> surroundingsMap = new HashMap<>();

    public Map<String, String> getSurroundingsMap() {
        return surroundingsMap;
    }

    public void setSurroundingsMap(Map<String, String> surroundingsMap) {
        this.surroundingsMap = surroundingsMap;
    }
    public Map<String, String> getCityMap() {
        return cityMap;
    }

    public void setCityMap(Map<String, String> cityMap) {
        this.cityMap = cityMap;
    }


    public Map<String, String> getMediaMap() {
        return mediaMap;
    }

    public void setMediaMap(Map<String, String> mediaMap) {
        this.mediaMap = mediaMap;
    }

    public Map<String, String> getTypeMap() {
        return typeMap;
    }


    public void setTypeMap(Map<String, String> typeMap) {
        this.typeMap = typeMap;
    }


    public void setRegular(boolean regular) {
        this.regular = regular;
    }

    public boolean isRegular() {
        return regular;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<String> getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(List<String> surroundings) {
        this.surroundings = surroundings;
    }

    public void setShapeMultiplier(double shapeMultiplier) {
        this.shapeMultiplier = shapeMultiplier;
    }

    public Double getShapeMultiplier() {
        return shapeMultiplier;
    }
}