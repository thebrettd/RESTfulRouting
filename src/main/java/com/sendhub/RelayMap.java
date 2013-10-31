package com.sendhub;

import java.util.*;

public class RelayMap {


    private Map<String,List<USPhoneNumber>> relayMapping;

    /***
     * This will create a HashMap whose key is a URL and whose value is a list of USPhoneNumbers that will be sent to
     * that relay.
     *
     * Assumes that all USPhoneNumbers passed in are valid.
     *
     * @param USPhoneNumbers
     */
    public RelayMap(List<USPhoneNumber> USPhoneNumbers) {
        this.relayMapping = new HashMap<String, List<USPhoneNumber>>(0);
        mapToRelays(USPhoneNumbers);
    }

    /***
     * Populate the relayMapping.
     * @param USPhoneNumbers
     */
    private void mapToRelays(List<USPhoneNumber> USPhoneNumbers) {
        if(USPhoneNumbers.size() > 0){
            List<USPhoneNumber> remainingUSPhoneNumbers = mapToRelay(USPhoneNumbers, 25, "10.0.4.0/24");
            remainingUSPhoneNumbers = mapToRelay(remainingUSPhoneNumbers, 10, "10.0.3.0/24");
            remainingUSPhoneNumbers = mapToRelay(remainingUSPhoneNumbers, 5, "10.0.2.0/24");
            mapToRelay(remainingUSPhoneNumbers, 1, "10.0.1.0/24");
        }
    }

    /***
     * Attempts to map phone numbers to a relay.
     *
     * If there are not enough phone numbers to fully utilize this relay, it will not use this relay.
     *
     * @param USPhoneNumbers The set of phone numbers with you are trying to map to the current relay
     * @param relayThroughput The throughput of the current relay
     * @param relayName The relay name (ip)
     * @return The remaining phone numbers which could not be sent through the current relay
     */
    private List<USPhoneNumber> mapToRelay(List<USPhoneNumber> USPhoneNumbers, int relayThroughput, String relayName) {
        int recipientsRemaining = USPhoneNumbers.size();
        int currRelayUsage = recipientsRemaining / relayThroughput;
        int totalMessagesForRelay = relayThroughput * currRelayUsage;

        if (currRelayUsage > 0){
            this.relayMapping.put(relayName, USPhoneNumbers.subList(0, totalMessagesForRelay));
        }
        return USPhoneNumbers.subList(totalMessagesForRelay, USPhoneNumbers.size());
    }

    public Map<String, List<USPhoneNumber>> getRelayMapping(){
        return this.relayMapping;
    }

}
