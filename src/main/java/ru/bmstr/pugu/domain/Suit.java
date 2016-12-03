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
    private Representative representative;

    public Suit() { }

    public Suit (Category category, Plaintiff plaintiff, Defendant defendant, SuitSumm initialSumm, SuitSumm agreedSumm, Result result, Representative representative) {
        this.setCategory(category);
        this.setPlaintiff(plaintiff);
        this.setDefendant(defendant);
        this.setInitialSumm(initialSumm);
        this.setAgreedSumm(agreedSumm);
        this.setResult(result);
        this.setRepresentative(representative);
    }

    public Category getCategory() {
        return category;
    }

    public Defendant getDefendant() {
        return defendant;
    }

    public Plaintiff getPlaintiff() {
        return plaintiff;
    }

    public SuitSumm getInitialSumm() {
        return initialSumm;
    }

    public SuitSumm getAgreedSumm() {
        return agreedSumm;
    }

    public Result getResult() {
        return result;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDefendant(Defendant defendant) {
        this.defendant = defendant;
    }

    public void setPlaintiff(Plaintiff plaintiff) {
        this.plaintiff = plaintiff;
    }

    public void setInitialSumm(SuitSumm initialSumm) {
        this.initialSumm = initialSumm;
    }

    public void setAgreedSumm(SuitSumm agreedSumm) {
        this.agreedSumm = agreedSumm;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }
}
