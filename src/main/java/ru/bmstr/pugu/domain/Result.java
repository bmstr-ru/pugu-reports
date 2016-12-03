package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Result {
    EMPTY(""),APPROVE("Удовлетворён"), DECLINE("Отказано"), NONE("Не рассмотрен");

    private String result;

    Result(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String toString() {
        return result;
    }
}
