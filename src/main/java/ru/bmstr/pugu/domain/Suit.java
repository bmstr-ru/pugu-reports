package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public class Suit {
    private Category category;
    private Defendant defendant;
    private Plaintiff plaintiff;
    private SuitSumm initialSumm;
    private SuitSumm agreedSumm;
    private Result result;

    public Suit (Category category, Plaintiff plaintiff, Defendant defendant, SuitSumm initialSumm, SuitSumm agreedSumm, Result result) {
        this.category = category;
        this.plaintiff = plaintiff;
        this.defendant = defendant;
        this.initialSumm = initialSumm;
        this.agreedSumm = agreedSumm;
        this.result = result;
    }
}
