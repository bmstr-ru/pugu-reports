package ru.bmstr.pugu.domain;

import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Created by bmstr on 10.12.2016.
 */
@Component
public class SuitComparator implements Comparator<Suit> {
    @Override
    public int compare(Suit suit1, Suit suit2) {
        return suit1.compareTo(suit2);
    }
}
