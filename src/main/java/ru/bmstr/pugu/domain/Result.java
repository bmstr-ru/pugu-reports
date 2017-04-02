package ru.bmstr.pugu.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 27.11.2016.
 */
@DatabaseTable
public class Result {
    public static Result APPROVED;
    public static Result DECLINED;
    public static Result UNRESOLVED;
    public static Result AGREED;

    public static final Result EMPTY_RESULT = new Result();

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    public String toString() {
        return getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean isEmpty(Result result) {
        return result == null || StringUtils.isEmpty(result.name);
    }

    public Result getOpposite() {
        if (this.equals(APPROVED)) {
            return DECLINED;
        } else if (this.equals(DECLINED)) {
            return APPROVED;
        } else {
            throw new RuntimeException("Cannot get opposite result of "+this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Result)) {
            return false;
        }
        return ((Result) obj).id == this.id;
    }

    @Override
    public int hashCode() {
        if (name == null) {
            return super.hashCode();
        } else {
            return name.hashCode();
        }
    }
}
