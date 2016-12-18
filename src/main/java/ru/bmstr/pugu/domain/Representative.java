package ru.bmstr.pugu.domain;

import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 03.12.2016.
 */
public enum Representative {
    EMPTY,
    BOITSOVA,
    AZARENKO,
    RANEVA,
    STRELNIKOV,
    BALANDINA,
    ABASOVA,
    ERMASHEVA,
    SUKHARNIKOVA,
    HARCHOV,
    BARANOVSKAYA,
    GILYAZOVA,
    BASTRAKOVA,
    CHUGINA,
    DARIA;

    public String toString() {
        return EnumNameHelper.getName(name()+".surname");
    }
}
