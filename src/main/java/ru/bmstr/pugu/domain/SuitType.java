package ru.bmstr.pugu.domain;

import ru.bmstr.pugu.beans.AllBeans;
import ru.bmstr.pugu.properties.EnumNameHelper;
import ru.bmstr.pugu.properties.PropertyLoader;

/**
 * Created by bmstr on 04.12.2016.
 */
public enum SuitType {
    USUAL,
    OUR,
    APPELATION,
    CASSATION,
    OUR_APPELATION,
    OUR_CASSATION;

    public String toString() {
        return EnumNameHelper.getName(name());
    }
}
