package com.sendhub;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class USPhoneNumberTest {
    @Test
    public void testValidateLength() throws Exception {
        assertTrue(USPhoneNumber.isValid("+12345678901"));

        assertFalse(USPhoneNumber.isValid("+1"));
        assertFalse(USPhoneNumber.isValid("+123456789012"));
    }

    @Test
    public void testNoLetters() throws Exception {
        assertFalse(USPhoneNumber.isValid("+123456789A"));
    }

    @Test
    public void testFirstChar() throws Exception {
        assertFalse(USPhoneNumber.isValid("12345678901"));
    }

    @Test
    public void testConstructor(){
        USPhoneNumber testPhone = new USPhoneNumber("+12345678901");

        assertTrue(testPhone.getAreaCode().equals("234"));
        assertTrue(testPhone.getCountryCode().equals("1"));
        assertTrue(testPhone.getNumber().equals("5678901"));
        assertTrue(testPhone.toString().equals("+12345678901"));
    }
}
