package ru.bmstr.pugu.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.properties.EnumNameHelper;

import java.util.Collections;
import java.util.List;

/**
 * Created by bmstr on 27.11.2016.
 */
@DatabaseTable
public class Category {

    public static final Category EMPTY_CATEGORY = new Category();

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
