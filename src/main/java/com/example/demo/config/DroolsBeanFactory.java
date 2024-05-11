package com.example.demo.config;

import com.example.demo.common.RulesEnum;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class DroolsBeanFactory {
    private final KieServices kieServices = KieServices.Factory.get();

    private KieFileSystem getKieFileSystem(RulesEnum rule) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(ResourceFactory.newClassPathResource(rule.getFileName()));
        return kieFileSystem;
    }

    public KieSession getKieSession(RulesEnum rule) {
        KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem(rule));
        kb.buildAll();

        KieRepository kieRepository = kieServices.getRepository();
        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);

        return kieContainer.newKieSession();
    }
}