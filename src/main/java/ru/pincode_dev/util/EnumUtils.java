package ru.pincode_dev.util;

import ru.pincode_dev.enums.UpdateField;

public class EnumUtils {
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

