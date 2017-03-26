package ru.bmstr.pugu.domain;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by bmstr on 26.03.2017.
 */
public class Appeal {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private Integer agreedSum;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Result result;

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

    public static AppealBuilder getBuilder() {
        return new AppealBuilder();
    }

    public static class AppealBuilder {
        private Appeal appeal = new Appeal();

        public AppealBuilder withAgreedSum(Integer agreedSum) {
            appeal.setAgreedSum(agreedSum);
            return this;
        }

        public AppealBuilder withResult(Result result) {
            appeal.setResult(result);
            return this;
        }

        public Appeal build() {
            return appeal;
        }
    }
}
