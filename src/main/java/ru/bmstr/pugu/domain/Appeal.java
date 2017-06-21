package ru.bmstr.pugu.domain;

import com.j256.ormlite.field.DatabaseField;
import org.springframework.util.StringUtils;

/**
 * Created by bmstr on 26.03.2017.
 */
public class Appeal implements Countable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private Integer agreedSum;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Result result;

    @DatabaseField
    private Integer year;

    public Appeal() {
    }

    public Appeal(Appeal originalAppeal) {
        this.id = originalAppeal.id;
        this.agreedSum = originalAppeal.agreedSum;
        this.result = originalAppeal.result;
        this.year = originalAppeal.year;
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

    public static AppealBuilder getBuilder() {
        return new AppealBuilder();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

        public AppealBuilder withYear(Integer year) {
            appeal.setYear(year);
            return this;
        }

        public Appeal build() {
            return appeal;
        }
    }
}
