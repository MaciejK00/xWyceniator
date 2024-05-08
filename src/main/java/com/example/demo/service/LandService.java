package com.example.demo.service;

import com.example.demo.entity.Land;
import com.example.demo.prices.MediaPrice;
import com.example.demo.prices.TypePrice;
import org.kie.api.runtime.KieSession;
import com.example.demo.config.DroolsBeanFactory;

public class LandService {

    public TypePrice suggestTypePrice(Land land, TypePrice typePrice) {
        KieSession kieSession = new DroolsBeanFactory().getKieSession();
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
        KieSession kieSession = new DroolsBeanFactory().getKieSession();
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
}
