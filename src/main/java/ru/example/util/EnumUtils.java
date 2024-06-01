package ru.example.util;

import ru.example.enums.UpdateField;

public class EnumUtils {
    // Метод для проверки, содержится ли строка в enum UpdateField

    /**
     * Проверка, является ли fieldName допустимым полем для обновления
     * @param fieldName поле, которое проверяем
     * @return true, если поле допустимо для обновления
     */
    public static boolean isValidUpdateField(String fieldName) {
        for (UpdateField field : UpdateField.values()) {
            if (field.getFieldName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}

