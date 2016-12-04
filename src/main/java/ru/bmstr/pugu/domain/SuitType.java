package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 04.12.2016.
 */
public enum SuitType {
    USUAL("Иск к нам"), OUR("Иск наш"), APPELATION("Жалобы не вступившие"), CASSATION("Жалобы вступившие");

    String name;
    SuitType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
