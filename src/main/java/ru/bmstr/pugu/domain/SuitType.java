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
    public static SuitType TO_US;
    public static SuitType SPECIAL;
    public static SuitType THIRD_PARTY;
    public static SuitType OUR;

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private Direction direction;

    @DatabaseField
    private String code;

    public String toString() {
        return code == null ? "" : code+". "+name;
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
        if ((obj == null) || !(obj instanceof SuitType)) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
