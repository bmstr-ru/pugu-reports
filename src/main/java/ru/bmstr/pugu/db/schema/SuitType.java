package ru.bmstr.pugu.db.schema;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 04.12.2016.
 */
@DatabaseTable
public class SuitType {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    public String toString() {
        return EnumNameHelper.getName(getName());
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SuitType)) {
            return false;
        }
        return ((SuitType) obj).id == this.id;
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
