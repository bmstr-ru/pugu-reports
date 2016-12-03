package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Defendant {
    EMPTY(""),PODRAZDELENIE("ГУ"), KAZNA("Казна"), MVD("МВД");

    String name;
    Defendant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
