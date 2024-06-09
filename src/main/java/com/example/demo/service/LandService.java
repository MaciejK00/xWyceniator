package com.example.demo.service;

import com.example.demo.common.*;
import com.example.demo.config.DroolsBeanFactory;
import com.example.demo.entity.Land;
import com.example.demo.prices.*;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.List;

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

    public List<LandTypePrice> getTypes(Land land, TypePrice typePrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.TYPE);
        List<LandTypePrice> typePrices = new ArrayList<>();
        try {
            kieSession.insert(land);
            kieSession.setGlobal("typePrice", typePrice);
            kieSession.fireAllRules();

            List<Object> objects = new ArrayList<>(kieSession.getObjects());

            for (Object media : objects) {
                if (media instanceof LandTypePrice landTypePrice) {
                    typePrices.add(landTypePrice);
                }

            }

        } finally {
            kieSession.dispose();
        }
        return typePrices;

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


    public List<MediaPriceFact> getmedia(Land land, MediaPrice mediaPrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.MEDIA);
        List<MediaPriceFact> mediaPriceFacts = new ArrayList<>();
        try {
            kieSession.insert(land);
            kieSession.setGlobal("mediaPrice", mediaPrice);
            kieSession.fireAllRules();

            List<Object> objects = new ArrayList<>(kieSession.getObjects());

            for (Object media : objects) {
                if (media instanceof MediaPriceFact mediaPriceFact) {
                    mediaPriceFacts.add(mediaPriceFact);
                }

            }

        } finally {
            kieSession.dispose();
        }

        return mediaPriceFacts;

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


    public List<SurroundingsPriceFact> getSurroundings(Land land, SurroundingsPrice surroundingsPrice) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.SURROUNDINGS);
        List<SurroundingsPriceFact> surroundingsPriceFacts = new ArrayList<>();
        try {
            kieSession.insert(land);
            kieSession.setGlobal("surroundingsPrice", surroundingsPrice);
            kieSession.fireAllRules();

            List<Object> objects = new ArrayList<>(kieSession.getObjects());

            for (Object object : objects) {
                if (object instanceof SurroundingsPriceFact surroundingsPriceFact) {
                    surroundingsPriceFacts.add(surroundingsPriceFact);
                }

            }

        } finally {
            kieSession.dispose();
        }
        System.out.println(surroundingsPrice.getPrice());
        return surroundingsPriceFacts;
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


    public List<CityMultiplier> getCities(Land land) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.CITY);
        List<CityMultiplier> cities = new ArrayList<>();
        try {
            kieSession.insert(land);
            kieSession.fireAllRules();

            List<Object> cityMultipliers = new ArrayList<>(kieSession.getObjects());

            for (Object cityMultiplier : cityMultipliers) {
                if (cityMultiplier instanceof CityMultiplier multiplier) {
                    cities.add(multiplier);
                }

            }

        } finally {
            kieSession.dispose();
        }

        return cities;
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