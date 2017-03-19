package ru.bmstr.pugu.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 27.11.2016.
 */
@DatabaseTable
public class Defendant {

    public static final Defendant EMPTY_DEFENDANT = new Defendant();

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    public String toString() {
        return name;
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

    public static boolean isEmpty(Defendant defendant) {
        return defendant == null || StringUtils.isEmpty(defendant.name);
    }
}
