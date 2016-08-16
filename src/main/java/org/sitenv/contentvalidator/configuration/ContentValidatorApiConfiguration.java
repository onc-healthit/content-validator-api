package org.sitenv.contentvalidator.configuration;

import org.sitenv.contentvalidator.parsers.CCDAParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

    @Bean
    public DocumentBuilder documentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory domFactory =  DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        return domFactory.newDocumentBuilder();
    }

    @Autowired
    @Bean
    public ScenarioManager scenarioManager(final Environment environment, CCDAParser ccdaParser){
        ScenarioManager scenarioManager;
        String scenarioDir = environment.getProperty("content.scenariosDir");
        scenarioManager = new ScenarioManager();
        scenarioManager.setScenarioFilePath(scenarioDir);
        scenarioManager.setCcdaParser(ccdaParser);
        return scenarioManager;
    }
}
