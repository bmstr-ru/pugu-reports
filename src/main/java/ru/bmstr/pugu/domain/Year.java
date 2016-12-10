package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 04.12.2016.
 */
public enum Year {
    Y2017(2017),
    Y2016(2016),
    Y2015(2015);

    int year;
    Year(int year) {
        this.year = year;
    }

    public String toString() {
        return String.valueOf(year);
    }
}
