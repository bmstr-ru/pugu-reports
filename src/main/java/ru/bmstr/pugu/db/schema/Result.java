package ru.bmstr.pugu.db.schema;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 27.11.2016.
 */
@DatabaseTable
public class Result {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    public Result() {
    }

    public Result(ru.bmstr.pugu.domain.Result result) {
        this.name = EnumNameHelper.getName(result.name());
    }

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
}
