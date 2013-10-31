package com.sendhub;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/route")
public class Router {
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Output routes(Input input){

        List<USPhoneNumber> validPhones = new ArrayList<USPhoneNumber>();
        List<String> invalidPhones = new ArrayList<String>();
        parsePhoneNumbers(input, validPhones, invalidPhones);

        RelayMap relayMap = new RelayMap(validPhones);
        return createOutput(input, relayMap, invalidPhones);
    }

    @POST
    @Produces("text/plain")
    @Consumes({"text/plain","text/html"})
    public String routes(){
        return "Please include valid JSON in your request body";
    }

    private Output createOutput(Input input, RelayMap relayMap, List<String> invalidPhones) {
        Output output = new Output();
        output.setMessage(input.getMessage());
        output.setRoutes(createRoutesFromMap(relayMap));

        output.setInvalidNumbers(invalidPhones);
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


    private void parsePhoneNumbers(Input input, List<USPhoneNumber> validPhones, List<String> invalidPhones) {
        for(String inputPhoneNumber : input.getRecipients()){
            if(USPhoneNumber.isValid(inputPhoneNumber)){
                validPhones.add(new USPhoneNumber(inputPhoneNumber));
            }else{
                invalidPhones.add(inputPhoneNumber);
            }
        }
    }

}
