package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public class SuitSumm {
    private double summ;

    public SuitSumm() {

    }

    public SuitSumm (double summ) {
        this.setSumm(summ);
    }

    public double getSumm() {
        return summ;
    }

    public String toString() {
        return Double.toString(getSumm());
    }

    public void setSumm(double summ) {
        this.summ = summ;
    }
}
