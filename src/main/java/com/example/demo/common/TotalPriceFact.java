package com.example.demo.common;

public class TotalPriceFact {
    double sizePrice = 0.0;

    int cityMultiplier = 1;

    double mediaPrice = 0.0;

    double typePrice = 0.0;

    double surroundingsPrice = 0.0;

    double shapeMultiplier = 1.0;
    double totalPrice = 0.0;
    boolean needRecalculation = false;

    public double getSizePrice() {
        return sizePrice;
    }

    public void setSizePrice(double sizePrice) {
        this.sizePrice = sizePrice;
    }

    public int getCityMultiplier() {
        return cityMultiplier;
    }

    public void setCityMultiplier(int cityMultiplier) {
        this.cityMultiplier = cityMultiplier;
    }

    public double getMediaPrice() {
        return mediaPrice;
    }

    public void setMediaPrice(double mediaPrice) {
        this.mediaPrice = mediaPrice;
    }

    public double getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(double typePrice) {
        this.typePrice = typePrice;
    }

    public double getSurroundingsPrice() {
        return surroundingsPrice;
    }

    public void setSurroundingsPrice(double surroundingsPrice) {
        this.surroundingsPrice = surroundingsPrice;
    }

    public double getShapeMultiplier() {
        return shapeMultiplier;
    }

    public void setShapeMultiplier(double shapeMultiplier) {
        this.shapeMultiplier = shapeMultiplier;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isNeedRecalculation() {
        return needRecalculation;
    }

    public void setNeedRecalculation(boolean needRecalculation) {
        this.needRecalculation = needRecalculation;
    }

    @Override
    public String toString() {
        return "TotalPriceFact{" +
                "sizePrice=" + sizePrice +
                ", cityMultiplier=" + cityMultiplier +
                ", mediaPrice=" + mediaPrice +
                ", typePrice=" + typePrice +
                ", surroundingsPrice=" + surroundingsPrice +
                ", shapeMultiplier=" + shapeMultiplier +
                '}';
    }
}
