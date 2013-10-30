package com.sendhub;

public class PhoneNumber {

    private String countryCode;
    private String areaCode;
    private String number;

    public PhoneNumber(String inputPhoneNumber){
        //todo: properly parse
        this.number = inputPhoneNumber;
    }

    public static boolean isValid(String phoneNumber) {
        //todo validate phone numbers
        return true;
    }

    @Override
    public String toString(){
        return "+" + countryCode + areaCode + number;
    }
}
