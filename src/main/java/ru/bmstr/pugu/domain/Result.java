package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Result {
    APPROVE("Удовлетворён"), DECLINE("Отклонён"), NONE("Не рассмотрен");

    private String result;

    Result(String result) {
        this.result = result;
    }

    public String toString() {
        return result;
    }
}
