package ru.bmstr.pugu.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by bmstr on 11.12.2016.
 */
@Component
public class PropertyLoader {

    private final static String DEFAULT_XML_PROPERTIES_FOLDER = "i18n";
    private static final Logger log = LogManager.getLogger(PropertyLoader.class);

    private Properties props;

    @PostConstruct
    private void loadProperties() {
        props = new Properties();
        Properties currentProperties = new Properties();
        Arrays.stream(new File(DEFAULT_XML_PROPERTIES_FOLDER).listFiles())
                .filter(file -> file.getName().endsWith(".xml"))
                .forEach(file -> {
                    try {
                        currentProperties.loadFromXML(new FileInputStream(file));
                        props.putAll(currentProperties);
                    } catch (IOException e) {
                        log.error("Could not load properties from file {}", file.getAbsolutePath(), e);
                    }
                });
    }

    public String getProperty(String propertyName) {
        return props.getProperty(propertyName, "");
    }
}
