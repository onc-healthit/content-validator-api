package org.sitenv.contentvalidator.configuration;

import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.parsers.CCDAParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.util.HashMap;

/**
 * Created by Brian on 2/5/2016.
 */
@Configuration
@ComponentScan("org.sitenv.contentvalidator")
public class ContentValidatorApiConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocalOverride(true);
        return propertySourcesPlaceholderConfigurer;
    }

    @Autowired
    @Bean
    public ScenarioLoader scenarioLoader(final Environment environment, CCDAParser ccdaParser){
        ScenarioLoader scenarioLoader;
        String scenarioDir = environment.getProperty("content.scenariosDir");
        scenarioLoader = new ScenarioLoader();
        scenarioLoader.setScenarioFilePath(scenarioDir);
        scenarioLoader.setCcdaParser(ccdaParser);
        return scenarioLoader;
    }

    @Autowired
    @Bean
    public HashMap<String, CCDARefModel> refModelHashMap(ScenarioLoader scenarioLoader){
        return scenarioLoader.getRefModelHashMap();
    }
}
