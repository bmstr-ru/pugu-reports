package ru.bmstr.pugu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by bmstr on 27.11.2016.
 */
@DatabaseTable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Suit implements Comparable<Suit>, Countable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private SuitType type;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Category category;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Defendant defendant;

    @DatabaseField
    private String plaintiff;

    @DatabaseField
    private Integer initialSumm;

    @DatabaseField
    private Integer agreedSumm;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Result result;

    @DatabaseField
    private Integer year;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Representative representative;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Appeal appeal;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Cassation cassation;

    private boolean adjusted = false;

    public Suit() {

    }

    public Suit(Suit originalSuit) {
        this.id = originalSuit.id;
        this.type = originalSuit.type;
        this.category = originalSuit.category;
        this.defendant = originalSuit.defendant;
        this.plaintiff = originalSuit.plaintiff;
        this.initialSumm = originalSuit.initialSumm;
        this.agreedSumm = originalSuit.agreedSumm;
        this.result = originalSuit.result;
        this.year = originalSuit.year;
        this.representative = originalSuit.representative;
        if (originalSuit.appeal != null) {
            this.appeal = new Appeal(originalSuit.appeal);
        }
        if (originalSuit.cassation != null) {
            this.cassation = new Cassation(originalSuit.cassation);
        }
    }

    public Object getAt(int index) {
        switch (index) {
            case 1:
                return getRepresentative();
            case 2:
                return getYear();
            case 3:
                return getCategory() != null ? getCategory() : getType();
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

    public int getCategoryId() {
        if (getCategory() == null) {
            return 0;
        } else {
            return getCategory().getId();
        }
    }

    public Defendant getDefendant() {
        return defendant;
    }

    public String getPlaintiff() {
        if (plaintiff == null) {
            return "";
        } else {
            return plaintiff;
        }
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

    @Override
    public Integer getAgreedSum() {
        return getAgreedSumm();
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

    public Integer getYear() {
        return year;
    }

    public int getYearId() {
        if (getYear() == null) {
            return 0;
        } else {
            return getYear();
        }
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public static SuitBuilder getBuilder() {
        return new SuitBuilder();
    }

    public SuitType getType() {
        return type;
    }

    public int getTypeId() {
        if (getType() == null) {
            return 0;
        } else {
            return getType().getId();
        }
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Suit suit2) {
        int compareResult = this.getTypeId() - suit2.getTypeId();
        if (compareResult == 0) {
            compareResult = this.getCategoryId() - suit2.getCategoryId();
            if (compareResult == 0) {
                compareResult = this.getYearId() - suit2.getYearId();
                if (compareResult == 0) {
                    compareResult = this.getPlaintiff().compareTo(suit2.getPlaintiff());
                }
            }
        }
        return compareResult;
    }

    public Appeal getAppeal() {
        return appeal;
    }

    public void setAppeal(Appeal appeal) {
        this.appeal = appeal;
    }

    public Cassation getCassation() {
        return cassation;
    }

    public void setCassation(Cassation cassation) {
        this.cassation = cassation;
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

        public SuitBuilder withYear(Integer year) {
            suit.setYear(year);
            return this;
        }

        public SuitBuilder withRepresentative(Representative representative) {
            suit.setRepresentative(representative);
            return this;
        }

        public SuitBuilder withAppeal(Appeal appeal) {
            suit.setAppeal(appeal);
            return this;
        }

        public SuitBuilder withCassation(Cassation cassation) {
            suit.setCassation(cassation);
            return this;
        }

        public Suit build() {
            return suit;
        }
    }

    public Suit finalSuit() {
        Suit finalSuit = new Suit(this);
        if (appeal != null) {
            if (Result.UNRESOLVED.equals(appeal.getResult()) || Result.isEmpty(appeal.getResult())) {
                finalSuit.setResult(Result.UNRESOLVED);
                finalSuit.setAgreedSumm(0);
            } else if (Result.DECLINED.equals(appeal.getResult())) {
                finalSuit.setResult(result);
            } else if (Result.APPROVED.equals(appeal.getResult())) {
                finalSuit.setResult(result.opposite());
                if (appeal.getAgreedSum() > 0) {
                    finalSuit.setAgreedSumm(appeal.getAgreedSum());
                }
            } else if (Result.AGREED.equals(appeal.getResult())) {
                finalSuit.setResult(Result.AGREED);
                finalSuit.setAgreedSumm(appeal.getAgreedSum());
            }
        }
        finalSuit.adjustSums();
        return finalSuit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("representative", representative)
                .append("category", category)
                .append("type", type)
                .append("plaintiff", plaintiff)
                .append("defendant", defendant)
                .append("initialSumm", initialSumm)
                .append("agreedSumm", agreedSumm)
                .append("year", year)
                .append("result", result)
                .toString()
                ;
    }

    private void adjustSums() {
        if (!adjusted) {
            if (agreedSumm > 0) {
                agreedSumm = (agreedSumm + 500) / 1000;
            }
            if (initialSumm > 0) {
                initialSumm = (initialSumm + 500) / 1000;
            }
            adjusted = true;
        }
    }
}
