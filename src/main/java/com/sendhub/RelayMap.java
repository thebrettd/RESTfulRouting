package com.sendhub;

import java.util.*;

public class RelayMap {


    private Map<String,List<PhoneNumber>> relayMapping;

    public RelayMap(List<PhoneNumber> phoneNumbers) {
        this.relayMapping = new HashMap<String, List<PhoneNumber>>(0);
        mapToRelays(phoneNumbers);
    }

    /***
     * Populate the relayMapping.
     * @param phoneNumbers
     */
    private void mapToRelays(List<PhoneNumber> phoneNumbers) {

        List<PhoneNumber> remainingPhoneNumbers = mapToRelay(phoneNumbers, 25, "10.0.4.0/24");
        remainingPhoneNumbers = mapToRelay(remainingPhoneNumbers, 10, "10.0.3.0/24");
        remainingPhoneNumbers = mapToRelay(remainingPhoneNumbers, 5, "10.0.2.0/24");
        remainingPhoneNumbers = mapToRelay(remainingPhoneNumbers, 1, "10.0.1.0/24");
    }

    /***
     * Attempts to map phone numbers to a relay.
     *
     * If there are not enough phone numbers to fully utilize this relay, it will not use this relay.
     *
     *
     *
     * @param phoneNumbers The set of phone numbers with you are trying to map to the current relay
     * @param relayThroughput The throughput of the current relay
     * @param relayName The relay name (ip)
     * @return The remaining phone numbers which could not be sent through the current relay
     */
    private List<PhoneNumber> mapToRelay(List<PhoneNumber> phoneNumbers, int relayThroughput, String relayName) {
        int recipientsRemaining = phoneNumbers.size();
        int currRelayUsage = recipientsRemaining / relayThroughput;
        int totalMessagesForRelay = relayThroughput * currRelayUsage;

        if (currRelayUsage > 0){
            this.relayMapping.put(relayName, phoneNumbers.subList(0, totalMessagesForRelay));
        }
        return phoneNumbers.subList(totalMessagesForRelay, phoneNumbers.size());
    }

    public Map<String, List<PhoneNumber>> getRelayMapping(){
        return this.relayMapping;
    }

}
