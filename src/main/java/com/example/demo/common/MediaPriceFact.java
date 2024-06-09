package com.example.demo.common;

import java.util.List;

public class MediaPriceFact {
    List<String> mediaList;
    Double price;

    public MediaPriceFact(List<String> media, Double price) {
        this.mediaList = media;
        this.price = price;
    }

    public List<String> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<String> mediaList) {
        this.mediaList = mediaList;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
