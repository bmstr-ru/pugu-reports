package ru.bmstr.pugu.domain;

import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Defendant {
    EMPTY, PODRAZDELENIE, KAZNA, MVD;

    public String toString() {
        return EnumNameHelper.getName(name());
    }
}
