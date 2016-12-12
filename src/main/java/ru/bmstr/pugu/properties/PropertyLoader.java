package ru.bmstr.pugu.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by bmstr on 11.12.2016.
 */
@Component
public class PropertyLoader {

    private final static String DEFAULT_XML_PROPERTIES_FILE = "i18n.xml";
    private static final Logger log = LogManager.getLogger(PropertyLoader.class);

    private Properties props;

    @PostConstruct
    private void loadProperties() {
        props = new Properties();
        try {
            props.loadFromXML(new FileInputStream(DEFAULT_XML_PROPERTIES_FILE));
        } catch (IOException e) {
            log.error("Could not load properties file", e);
        }
    }

    public String getProperty(String propertyName) {
        return props.getProperty(propertyName, "");
    }
}
