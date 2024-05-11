package com.example.demo.service;

import com.example.demo.common.RulesEnum;
import com.example.demo.config.DroolsBeanFactory;
import com.example.demo.entity.Land;
import com.example.demo.prices.*;
import org.kie.api.runtime.KieSession;

public class LandService {
    private KieSession kieSession;

    public TypePrice suggestTypePrice(Land land, TypePrice typePrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.TYPE);
        try {
            kieSession.insert(land);
            kieSession.setGlobal("typePrice", typePrice);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(typePrice.getPrice());
        return typePrice;

    }

    public MediaPrice suggestMediaPrice(Land land, MediaPrice mediaPrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.MEDIA);
        try {
            kieSession.insert(land);
            kieSession.setGlobal("mediaPrice", mediaPrice);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(mediaPrice.getPrice());
        return mediaPrice;

    }

    public SizePrice suggestSizePrice(Land land, SizePrice sizePrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.SIZE);
        try {
            kieSession.insert(land);
            kieSession.setGlobal("sizePrice", sizePrice);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(sizePrice.getPrice());
        return sizePrice;
    }

    public SurroundingsPrice suggestSurroundingsPrice(Land land, SurroundingsPrice surroundingsPrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.SURROUNDINGS);
        try {
            kieSession.insert(land);
            kieSession.setGlobal("surroundingsPrice", surroundingsPrice);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(surroundingsPrice.getPrice());
        return surroundingsPrice;
    }

    public CityPrice suggestCityPrice(Land land, CityPrice cityPrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.CITY);
        try {
            kieSession.insert(land);
            kieSession.setGlobal("cityPrice", cityPrice);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(cityPrice.getMultiplier());
        return cityPrice;
    }

    public ShapePrice suggestShapePrice(Land land, ShapePrice shapePrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.SHAPE);
        try {
            kieSession.insert(land);
            kieSession.setGlobal("shapePrice", shapePrice);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(shapePrice.getShapeMultiplier());
        return shapePrice;
    }
}