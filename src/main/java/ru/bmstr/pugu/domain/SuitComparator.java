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
        int compareResult = suit1.getType().compareTo(suit2.getType());
        if (compareResult == 0) {
            compareResult = suit1.getCategory().compareTo(suit2.getCategory());
            if (compareResult == 0) {
                compareResult = suit1.getYear().compareTo(suit2.getYear());
                if (compareResult == 0) {
                    compareResult = suit1.getPlaintiff().compareTo(suit2.getPlaintiff());
                }
            }
        }
        return compareResult;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SuitComparator)) {
            return true;
        } else {
            return false;
        }
    }
}
