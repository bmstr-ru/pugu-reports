package ru.bmstr.pugu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Comparator;

/**
 * Created by bmstr on 27.11.2016.
 */
public class Suit {
    private SuitType type;
    private Category category;
    private Defendant defendant;
    private String plaintiff;
    private Integer initialSumm;
    private Integer agreedSumm;
    private Result result;
    private Year year;
    private Representative representative;

    public Object getAt(int index) {
        switch (index) {
            case 1:
                return getYear();
            case 2:
                return getType();
            case 3:
                return getCategory();
            case 4:
                return getPlaintiff();
            case 5:
                return getInitialSumm();
            case 6:
                return getDefendant();
            case 7:
                return getResult();
            case 8:
                return getAgreedSumm();
            default:
                throw new RuntimeException("Tried to get column number " + index + " which is missing");
        }
    }

    public Category getCategory() {
        return category;
    }

    public Defendant getDefendant() {
        return defendant;
    }

    public String getPlaintiff() {
        return plaintiff;
    }

    public Integer getInitialSumm() {
        return initialSumm;
    }

    public Integer getAgreedSumm() {
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

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public void setInitialSumm(Integer initialSumm) {
        this.initialSumm = initialSumm;
    }

    public void setAgreedSumm(Integer agreedSumm) {
        this.agreedSumm = agreedSumm;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public static SuitBuilder getBuilder() {
        return new SuitBuilder();
    }

    public SuitType getType() {
        return type;
    }

    public void setType(SuitType type) {
        this.type = type;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }

    public static class SuitBuilder {
        private Suit suit = new Suit();

        public SuitBuilder withCategory(Category category) {
            suit.setCategory(category);
            return this;
        }

        public SuitBuilder withDefendant(Defendant defendant) {
            suit.setDefendant(defendant);
            return this;
        }

        public SuitBuilder withPlaintiff(String plaintiff) {
            suit.setPlaintiff(plaintiff);
            return this;
        }

        public SuitBuilder withInitialSumm(Integer initialSumm) {
            suit.setInitialSumm(initialSumm);
            return this;
        }

        public SuitBuilder withAgreedSumm(Integer agreedSumm) {
            suit.setAgreedSumm(agreedSumm);
            return this;
        }

        public SuitBuilder withResult(Result result) {
            suit.setResult(result);
            return this;
        }

        public SuitBuilder withType(SuitType type) {
            suit.setType(type);
            return this;
        }

        public SuitBuilder withYear(Year year) {
            suit.setYear(year);
            return this;
        }

        public SuitBuilder withRepresentative(Representative representative) {
            suit.setRepresentative(representative);
            return this;
        }

        public Suit getSuit() {
            return suit;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("representative",getRepresentative().toString())
                .append("category",getCategory().toString())
                .append("type", getType().toString())
                .append("plaintiff",getPlaintiff().toString())
                .append("defendant",getDefendant().toString())
                .append("initialSumm",String.valueOf(initialSumm))
                .append("agreedSumm",String.valueOf(agreedSumm))
                .append("year",getYear().toString())
                .append("result",getResult().toString())
                .toString()
                ;
    }
}
