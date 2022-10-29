package io.jmix.bookstore.employee;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PositionColor implements EnumClass<String> {

    GREEN("GREEN", "position-green"),
    RED("RED", "position-red"),
    YELLOW("YELLOW", "position-yellow"),
    PURPLE("PURPLE", "position-purple"),
    BLUE("BLUE", "position-blue"),
    ;

    private String id;
    private String styleName;

    PositionColor(String id, String styleName) {
        this.id = id;
        this.styleName = styleName;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PositionColor fromId(String id) {
        for (PositionColor at : PositionColor.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }

    public String getStyleName() {
        return styleName;
    }
}
