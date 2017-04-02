package ru.bmstr.pugu.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.db.DatabaseManager;
import ru.bmstr.pugu.properties.EnumNameHelper;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bmstr on 27.11.2016.
 */
@DatabaseTable
public class Category {

    public static final Category EMPTY_CATEGORY = new Category();

    @Autowired
    private DatabaseManager databaseManager;

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private SuitType type;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Category parent;

    @DatabaseField
    private String name;

    @DatabaseField
    private String code;

    public String toString() {
        return code == null ? "" : code+". "+name;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Category)) {
            return false;
        }
        return ((Category) obj).id == this.id;
    }

    @Override
    public int hashCode() {
        if (code == null) {
            return super.hashCode();
        } else {
            return code.hashCode();
        }
    }

    public boolean hasParent(Category searchParent) {
        if (this.equals(searchParent) || searchParent.equals(this.getParent())) {
            return true;
        } else {
            if (this.getParent() == null) {
                return false;
            } else {
                return this.getParent().hasParent(searchParent);
            }
        }
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static boolean isEmpty(Category category) {
        return category == null || StringUtils.isEmpty(category.name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
