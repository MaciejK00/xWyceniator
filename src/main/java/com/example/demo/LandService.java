package com.example.demo;

import org.kie.api.runtime.KieSession;
import com.example.demo.config.DroolsBeanFactory;

public class LandService {
    KieSession kieSession = new DroolsBeanFactory().getKieSession();

    public Cena suggestPrice(Land land,Cena cena) {
        try {
            kieSession.insert(land);
            kieSession.setGlobal("cena", cena);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(cena.getPrice());
        return  cena;

    }
}
