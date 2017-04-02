package ru.bmstr.pugu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 03.12.2016.
 */
@DatabaseTable
@JsonIgnoreProperties({ "shortFio", "fullFio" })
public class Representative {

    public static final Representative EMPTY_REPRESENTATIVE = new Representative();

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String midname;

    @DatabaseField
    private String surname;

    public String toString() {
        return surname;
    }

    public String getFullFio() {
        return surname + " " + name + " " + midname;
    }

    public String getShortFio() {
        return surname +
                (StringUtils.isEmpty(name) ? "" : " " + name.substring(0, 1) + ".") +
                (StringUtils.isEmpty(midname) ? "" : " " + midname.substring(0, 1) + ".");
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

    public static boolean isEmpty(Representative representative) {
        return representative == null || StringUtils.isEmpty(representative.surname);
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Representative)) {
            return false;
        }
        return ((Representative) obj).id == this.id;
    }

    @Override
    public int hashCode() {
        return getFullFio().hashCode();
    }

}
