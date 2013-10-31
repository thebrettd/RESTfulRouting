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
        List<USPhoneNumber> USPhoneNumbers = parsePhoneNumbers(input);
        RelayMap relayMap = new RelayMap(USPhoneNumbers);
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
        Map<String,List<USPhoneNumber>> relayMapping = relayMap.getRelayMapping();

        for(String usedRelay : relayMapping.keySet()){
            Route currRoute = new Route();
            currRoute.setIp(usedRelay);
            currRoute.setRecipients(extractPhones(relayMapping.get(usedRelay)));
            routes.add(currRoute);
        }

        return routes;
    }

    private List<String> extractPhones(List<USPhoneNumber> USPhoneNumbers) {
        List<String> phoneNumberStrings = new ArrayList<String>();
        for(USPhoneNumber currPhone : USPhoneNumbers){
            phoneNumberStrings.add(currPhone.toString());
        }
        return phoneNumberStrings;
    }

    private List<USPhoneNumber> parsePhoneNumbers(Input input) {
        List<USPhoneNumber> recipientsList = new ArrayList<USPhoneNumber>();
        for(String inputPhoneNumber : input.getRecipients()){
            if(USPhoneNumber.isValid(inputPhoneNumber)){
                USPhoneNumber phone = new USPhoneNumber(inputPhoneNumber);
                recipientsList.add(phone);
            }
        }
        return recipientsList;
    }

}
