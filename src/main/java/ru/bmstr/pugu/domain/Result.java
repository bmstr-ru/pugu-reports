package ru.bmstr.pugu.domain;

import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Result {
    EMPTY, APPROVE, DECLINE, NONE;

   public String toString() {
        return EnumNameHelper.getName(name());
    }
}
