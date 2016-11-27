package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public class Plaintiff {
    private String name;

    public Plaintiff (String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
