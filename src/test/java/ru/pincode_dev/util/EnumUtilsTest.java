package ru.pincode_dev.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnumUtilsTest {
    @Test
    public void testIsValidUpdateField_ValidField() {
        assertTrue(EnumUtils.
                isValidUpdateField("isSearchable"));
    }

    @Test
    public void testIsValidUpdateField_InvalidField() {
        assertFalse(EnumUtils.
                isValidUpdateField("isDropDatabase"));
    }

    @Test
    public void testIsValidUpdateField_EmptyField() {
        assertFalse(EnumUtils.
                isValidUpdateField(""));
    }
}
