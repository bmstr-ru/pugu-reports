package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Defendant {
    PODRAZDELENIE("Подразделение"), KAZNA("Казна"), MVD("МВД");

    String name;
    Defendant(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
