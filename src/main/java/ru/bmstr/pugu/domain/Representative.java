package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 03.12.2016.
 */
public enum Representative {
    EMPTY("","",""),
    BOITSOVA("Ольга", "Николаевна", "Бойцова"),
    AZARENKO("Илья", "Викторович", "Азаренко"),
    RANEVA("Анна", "Александровна", "Ранева"),
    STRELNIKOV("Вадим", "Александрович", "Стрельников"),
    BALANDINA("Анна", "Николаевна", "Баландина"),
    ABASOVA("Мария", "", "Абасова"),
    ERMASHEVA("Анастасия", "Александровна", "Ермашёва"),
    SUKHARNIKOVA("Александра", "Викторовна", "Сухарникова"),
    HARCHOV("Владислав", "", "Харчев"),
    BARANOVSKAYA("Анна", "Викторовна", "Барановская"),
    GILYAZOVA("Альмира", "Рашидовна", "Гилязова"),
    BASTRAKOVA("Екатерина", "", "Екатерина"),
    CHUGINA("Ирина", "Валерьевна", "Чугина"),
    DARIA("Даша", "Даша", "Даша")
    ;
    private String name;
    private String midname;
    private String surname;

    Representative (String name, String midNamae, String surname) {
        this.name = name;
        this.midname = midNamae;
        this.surname = surname;
    }

    public String toString() {
        return surname;
    }
}
