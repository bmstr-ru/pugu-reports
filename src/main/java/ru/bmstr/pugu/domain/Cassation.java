package ru.bmstr.pugu.domain;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by bmstr on 26.03.2017.
 */
public class Cassation implements Countable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private Integer agreedSum;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Result result;

    @DatabaseField
    private boolean inSupremeCourt;

    @DatabaseField
    private Integer year;

    public Cassation() {

    }

    public Cassation(Cassation originalCassation) {
        this.id = originalCassation.id;
        this.agreedSum = originalCassation.agreedSum;
        this.result = originalCassation.result;
        this.inSupremeCourt = originalCassation.inSupremeCourt;
        this.year = originalCassation.year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAgreedSum() {
        return agreedSum;
    }

    public void setAgreedSum(Integer agreedSum) {
        this.agreedSum = agreedSum;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public Cassation withAgreedSum(Integer agreedSum) {
        this.agreedSum = agreedSum;
        return this;
    }

    public Cassation withResult(Result result) {
        this.result = result;
        return this;
    }


    public static CassationBuilder getBuilder() {
        return new CassationBuilder();
    }

    public boolean isInSupremeCourt() {
        return inSupremeCourt;
    }

    public void setInSupremeCourt(boolean inSupremeCourt) {
        this.inSupremeCourt = inSupremeCourt;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public static class CassationBuilder {

        private Cassation cassation = new Cassation();

        public CassationBuilder withAgreedSum(Integer agreedSum) {
            cassation.setAgreedSum(agreedSum);
            return this;
        }

        public CassationBuilder withResult(Result result) {
            cassation.setResult(result);
            return this;
        }

        public CassationBuilder withYear(Integer year) {
            cassation.setYear(year);
            return this;
        }

        public CassationBuilder withInSupremeCourt(boolean inSupremeCourt) {
            cassation.setInSupremeCourt(inSupremeCourt);
            return this;
        }

        public Cassation build() {
            return cassation;
        }
    }
}
