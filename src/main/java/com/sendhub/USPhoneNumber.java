package com.sendhub;

public class USPhoneNumber {

    private String countryCode;
    private String areaCode;
    private String number;

    public USPhoneNumber(String inputPhoneNumber){
        this.countryCode = inputPhoneNumber.substring(1,2);
        this.areaCode = inputPhoneNumber.substring(2,5);
        this.number = inputPhoneNumber.substring(5,inputPhoneNumber.length());
    }

    public static boolean isValid(String phoneNumber) {
        if (!(phoneNumber.charAt(0) == '+')){
            return false;
        }

        if (phoneNumber.length() != 12){
            return false;
        }

        return true;
    }

    @Override
    public String toString(){
        return "+" + countryCode + areaCode + number;
    }


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
