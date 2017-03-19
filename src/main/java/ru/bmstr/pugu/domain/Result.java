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
}
