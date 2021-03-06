package com.sendhub;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class USPhoneNumberTest {
    @Test
    public void testValidateLength() throws Exception {
        assertTrue(USPhoneNumber.isValid("+12342355678"));

        assertFalse(USPhoneNumber.isValid("+1"));
        assertFalse(USPhoneNumber.isValid("+123423556781"));
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
        USPhoneNumber testPhone = new USPhoneNumber("+12342355678");

        assertTrue(testPhone.getAreaCode().equals("234"));
        assertTrue(testPhone.getCountryCode().equals("1"));
        assertTrue(testPhone.getExchangeCode().equals("235"));
        assertTrue(testPhone.toString().equals("+12342355678"));
    }

    @Test
    public void testIsValid(){
        assertTrue(USPhoneNumber.isValid("+12342355678"));
        assertFalse(USPhoneNumber.isValid("+123"));

    }
}
