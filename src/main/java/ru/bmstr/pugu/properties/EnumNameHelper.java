package ru.bmstr.pugu.properties;

import ru.bmstr.pugu.beans.AllBeans;

/**
 * Created by bmstr on 12.12.2016.
 */
public class EnumNameHelper {

    private static PropertyLoader propertyLoader;

    public static String getName(String enumName) {
        propertyLoader = AllBeans.getContext().getBean(PropertyLoader.class);
        return propertyLoader.getProperty("enum." + enumName);
    }
}
