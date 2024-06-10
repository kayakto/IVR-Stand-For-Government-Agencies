package ru.pincode_dev.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на обновление поля")
public class UpdateRequest {
    @Schema(description = "Название поля", example = "textSimple")
    public final String fieldName;
    @Schema(description = "Новое значение", example = "Замена паспорта при неизвестных обстоятельствах")
    public final Object newValue;

    public UpdateRequest(String fieldName, Object newValue) {
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getNewValue() {
        return newValue;
    }
}
