package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public class SuitSumm {
    private double summ;

    public SuitSumm (double summ) {
        this.summ = summ;
    }

    public String toString() {
        return Double.toString(summ);
    }
}
