package com.example.demo.entity;

import java.util.List;

public class Land {
    private String city;
    private List<String> media;
    private String type;
    private Long size;
    private boolean regular;
    private List<String> surroundings;
    private Integer cityMultiplier;
    private Double mediaPrice;
    private Double shapeMultiplier;
    private Double surroundingsPrice;
    private Double typePrice;
    private Double sizePrice;

    public Double getSizePrice() {
        return sizePrice;
    }

    public void setSizePrice(Double sizePrice) {
        this.sizePrice = sizePrice;
    }

    public Integer getCityMultiplier() {
        return cityMultiplier;
    }

    public void setCityMultiplier(Integer cityMultiplier) {
        this.cityMultiplier = cityMultiplier;
    }

    public Double getMediaPrice() {
        return mediaPrice;
    }

    public void setMediaPrice(Double mediaPrice) {
        this.mediaPrice = mediaPrice;
    }

    public Double getSurroundingsPrice() {
        return surroundingsPrice;
    }

    public void setSurroundingsPrice(Double surroundingsPrice) {
        this.surroundingsPrice = surroundingsPrice;
    }

    public Double getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(Double typePrice) {
        this.typePrice = typePrice;
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

    public Double getShapeMultiplier() {
        return shapeMultiplier;
    }

    public void setShapeMultiplier(Double shapeMultiplier) {
        this.shapeMultiplier = shapeMultiplier;
    }
}