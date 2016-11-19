package ru.bmstr.pugu.beans;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by bmstr on 19.11.2016.
 */
@Component
public class SimpleBean {
    private static final Logger log = LogManager.getLogger(SimpleBean.class);
    public static void test() {
        log.debug("Ваще чума!");
    }
}
