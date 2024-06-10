package com.example.demo.service;

import com.example.demo.common.*;
import com.example.demo.config.DroolsBeanFactory;
import com.example.demo.entity.Land;
import com.example.demo.prices.*;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LandService {
    private KieSession kieSession;


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




    public List<MediaPriceFact> getMedia(Land land, MediaPrice mediaPrice) {
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

    public Map<String, String> getCityMap(Land land) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.CITY);
        Map<String, String> map = new HashMap<>();
        try {
            kieSession.insert(land);
            kieSession.setGlobal("cityPrice", new CityPrice());

            kieSession.fireAllRules();

            List<Object> cityMultipliers = new ArrayList<>(kieSession.getObjects());

            for (Object cityMultiplier : cityMultipliers) {
                if (cityMultiplier instanceof CityMultiplier multiplier) {
                    map.put(multiplier.getCity(), String.valueOf(multiplier.getMultiplier()));
                }
            }

        } finally {
            kieSession.dispose();
        }
        return map;
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

    public Map<String, String> getMediaMap(Land land) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.MEDIA);
        Map<String, String> map = new HashMap<>();
        try {
            kieSession.setGlobal("mediaPrice", new MediaPrice());

            kieSession.insert(land);
            kieSession.fireAllRules();

            List<Object> cityMultipliers = new ArrayList<>(kieSession.getObjects());

            for (Object cityMultiplier : cityMultipliers) {
                if (cityMultiplier instanceof MediaPriceFact multiplier) {
                    map.put(multiplier.getMediaList().get(0), String.valueOf(multiplier.getPrice()));
                }
            }

        } finally {
            kieSession.dispose();
        }
        return map;
    }

    public Map<String, String> getTypeMap(Land land) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.TYPE);
        Map<String, String> map = new HashMap<>();
        try {
            kieSession.insert(land);
            kieSession.setGlobal("typePrice", new TypePrice());

            kieSession.fireAllRules();

            List<Object> cityMultipliers = new ArrayList<>(kieSession.getObjects());

            for (Object cityMultiplier : cityMultipliers) {
                if (cityMultiplier instanceof LandTypePrice multiplier) {
                    map.put(multiplier.getType(), String.valueOf(multiplier.getPrice()));
                }
            }

        } finally {
            kieSession.dispose();
        }
        return map;
    }



    public Map<String, String> getSurroundingsMap(Land land) {
        kieSession = new DroolsBeanFactory().getKieSession(RulesEnum.SURROUNDINGS);
        Map<String, String> map = new HashMap<>();
        try {
            kieSession.insert(land);
            kieSession.setGlobal("surroundingsPrice", new SurroundingsPrice());

            kieSession.fireAllRules();

            List<Object> cityMultipliers = new ArrayList<>(kieSession.getObjects());

            for (Object cityMultiplier : cityMultipliers) {
                if (cityMultiplier instanceof SurroundingsPriceFact multiplier) {
                    map.put(multiplier.getSurroundingsList().get(0), String.valueOf(multiplier.getPrice()));
                }
            }

        } finally {
            kieSession.dispose();
        }
        return map;
    }

}