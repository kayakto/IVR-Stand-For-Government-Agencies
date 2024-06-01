package ru.example.enums;

public enum UpdateField {
    TEXT_SIMPLE("textSimple"),
    VIDEO_URL("videoURL"),
    CHILDREN("children"),
    INFO_CHILDREN("infoChildren"),
    IS_SEARCHABLE("isSearchable"),
    ICON_URL("iconURL");

    private final String fieldName;

    UpdateField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
