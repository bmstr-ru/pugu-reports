package ru.bmstr.pugu.domain;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 04.12.2016.
 */
@DatabaseTable
public class SuitType {

    public static final SuitType EMPTY_SUIT_TYPE = new SuitType();

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private Direction direction;

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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public static boolean isEmpty(SuitType suitType) {
        return suitType == null || StringUtils.isEmpty(suitType.name);
    }
}
