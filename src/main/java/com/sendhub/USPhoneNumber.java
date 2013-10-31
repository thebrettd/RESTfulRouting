package com.sendhub;

public class USPhoneNumber {

    private String countryCode;
    private String areaCode;
    private String exchangeCode;
    private String subscriberNumber;

    public USPhoneNumber(String inputPhoneNumber){
        this.countryCode = inputPhoneNumber.substring(1,2);
        this.areaCode = inputPhoneNumber.substring(2,5);
        this.exchangeCode = inputPhoneNumber.substring(5,8);
        this.subscriberNumber = inputPhoneNumber.substring(8,inputPhoneNumber.length());
    }

    /***
     * http://en.wikipedia.org/wiki/North_American_Numbering_Plan
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isValid(String phoneNumber) {
        if (!(phoneNumber.charAt(0) == '+')){
            return false;
        }

        if (phoneNumber.length() != 12){
            return false;
        }

        String potentialCountryCode = phoneNumber.substring(1,2);
        String potentialAreaCode = phoneNumber.substring(2,5);
        String potentialExchangeCode = phoneNumber.substring(5,8);
        String potentialSubscriberNumber = phoneNumber.substring(8,phoneNumber.length());

        return validAreaCode(potentialAreaCode) && validExchangeCode(potentialExchangeCode) && validSubscriberNumber(potentialSubscriberNumber);
    }

    //[0-9]
    private static boolean validSubscriberNumber(String potentialSubscriberNumber) {
        return potentialSubscriberNumber.matches("[0-9][0-9][0-9][0-9]");
    }

    private static boolean validExchangeCode(String potentialExchangeCode) {
        return potentialExchangeCode.substring(0,1).matches("[2-9]") && lastTwoExchangeDigitsValid(potentialExchangeCode.substring(1,3));
    }

    private static boolean lastTwoExchangeDigitsValid(String substring) {
        if (substring.charAt(0) == '1' && substring.charAt(1) == '1'){
            return false;
        }else{
            return substring.matches("[0-9][0-9]");
        }

    }

    private static boolean validAreaCode(String potentialAreaCode) {
        return potentialAreaCode.matches("[2-9][0-9][0-9]");
    }

    @Override
    public String toString(){
        return "+" + countryCode + areaCode + exchangeCode + subscriberNumber;
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

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }
}
