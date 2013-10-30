package com.sendhub;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/route")
public class Router {
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Output routes(Input input){

        //todo capture invalid phones
        List<PhoneNumber> phoneNumbers = parsePhoneNumbers(input);
        RelayMap relayMap = new RelayMap(phoneNumbers);
        return createOutput(input, relayMap);
    }

    @POST
    @Produces("text/plain")
    @Consumes({"text/plain","text/html"})
    public String routes(){
        return "Please include valid JSON in your request body";
    }

    private Output createOutput(Input input, RelayMap relayMap) {
        Output output = new Output();
        output.setMessage(input.getMessage());
        output.setRoutes(createRoutesFromMap(relayMap));
        //todo output invalid phone numbers in response
        return output;
    }

    private List<Route> createRoutesFromMap(RelayMap relayMap) {
        List<Route> routes = new ArrayList<Route>();
        Map<String,List<PhoneNumber>> relayMapping = relayMap.getRelayMapping();

        for(String usedRelay : relayMapping.keySet()){
            Route currRoute = new Route();
            currRoute.setIp(usedRelay);
            currRoute.setRecipients(extractPhones(relayMapping.get(usedRelay)));
            routes.add(currRoute);
        }

        return routes;
    }

    private List<String> extractPhones(List<PhoneNumber> phoneNumbers) {
        List<String> phoneNumberStrings = new ArrayList<String>();
        for(PhoneNumber currPhone : phoneNumbers){
            phoneNumberStrings.add(currPhone.toString());
        }
        return phoneNumberStrings;
    }

    private List<PhoneNumber> parsePhoneNumbers(Input input) {
        List<PhoneNumber> recipientsList = new ArrayList<PhoneNumber>();
        for(String inputPhoneNumber : input.getRecipients()){
            if(PhoneNumber.isValid(inputPhoneNumber)){
                PhoneNumber phone = new PhoneNumber(inputPhoneNumber);
                recipientsList.add(phone);
            }
        }
        return recipientsList;
    }

}
