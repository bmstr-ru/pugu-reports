package ru.bmstr.pugu.db.schema;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.bmstr.pugu.db.DatabaseManager;
import ru.bmstr.pugu.properties.EnumNameHelper;

import java.util.Collections;
import java.util.List;

/**
 * Created by bmstr on 27.11.2016.
 */
@DatabaseTable
public class Category {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private SuitType type;

    @DatabaseField(foreign = true)
    private Category parent;

    @DatabaseField
    private String name;

    public String toString() {
        return EnumNameHelper.getName(getName());
    }

    private boolean hasParent(Category searchParent) {
        if (searchParent == this || (this.getParent() != null && this.getParent() == searchParent)) {
            return true;
        } else {
            if (this.getParent() == null) {
                return false;
            } else {
                return this.getParent().hasParent(searchParent);
            }
        }
    }

    public static List<Category> childsOf(Category searchParent) {
//        return Arrays.stream(Category.values())
//                .filter(child -> child.hasParent(searchParent))
//                .collect(Collectors.toList());
        return Collections.emptyList();
    }

    public SuitType getType() {
        return type;
    }

    public void setType(SuitType type) {
        this.type = type;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}