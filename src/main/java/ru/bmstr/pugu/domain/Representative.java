package ru.bmstr.pugu.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 03.12.2016.
 */
@DatabaseTable
public class Representative {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String midname;

    @DatabaseField
    private String surname;

    public String toString() {
        return EnumNameHelper.getName(getName() +".surname");
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

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
