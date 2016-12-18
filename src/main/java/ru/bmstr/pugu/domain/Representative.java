package ru.bmstr.pugu.domain;

import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 03.12.2016.
 */
public enum Representative {
    ALL,
    ABASOVA,
    AZARENKO,
    BALANDINA,
    BARANOVSKAYA,
    BASTRAKOVA,
    BOITSOVA,
    GILYAZOVA,
    ERMASHEVA,
    MERKULOVA,
    RANEVA,
    STRELNIKOV,
    SUKHARNIKOVA,
    CHUGINA,
    HARCHOV,
    EMPTY_REPRESENTATIVE;

    public String toString() {
        return EnumNameHelper.getName(name()+".surname");
    }
}
