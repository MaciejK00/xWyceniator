package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Land {
    private String city;
    private List<String> media = new ArrayList<>();
    private String type;
    private Long size;
    private boolean regular;
    private List<String> surroundings = new ArrayList<>();
    private Integer cityMultiplier;
    private Double mediaPrice;
    private Double shapeMultiplier;
    private Double surroundingsPrice;
    private Double typePrice;
    private Double sizePrice;
    private Double waterPrice;
    private Double gasPrice;
    private Double powerPrice;
    private Double sewerPrice;
    private Double expressPrice;
    private Double highwayPrice;
    private Double tarmacPrice;

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

    public Double getWaterPrice() {
        return waterPrice;
    }

    public void setWaterPrice(Double waterPrice) {
        this.waterPrice = waterPrice;
    }

    public Double getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(Double gasPrice) {
        this.gasPrice = gasPrice;
    }

    public Double getPowerPrice() {
        return powerPrice;
    }

    public void setPowerPrice(Double powerPrice) {
        this.powerPrice = powerPrice;
    }

    public Double getSewerPrice() {
        return sewerPrice;
    }

    public void setSewerPrice(Double sewerPrice) {
        this.sewerPrice = sewerPrice;
    }

    public Double getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(Double expressPrice) {
        this.expressPrice = expressPrice;
    }

    public Double getHighwayPrice() {
        return highwayPrice;
    }

    public void setHighwayPrice(Double highwayPrice) {
        this.highwayPrice = highwayPrice;
    }

    public Double getTarmacPrice() {
        return tarmacPrice;
    }

    public void setTarmacPrice(Double tarmacPrice) {
        this.tarmacPrice = tarmacPrice;
    }
}