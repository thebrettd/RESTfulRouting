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

        Map<String,List<USPhoneNumber>> validAndInvalidPhones = parsePhoneNumbers(input);
        RelayMap relayMap = new RelayMap(validAndInvalidPhones.get("valid"));
        return createOutput(input, relayMap, validAndInvalidPhones);
    }

    @POST
    @Produces("text/plain")
    @Consumes({"text/plain","text/html"})
    public String routes(){
        return "Please include valid JSON in your request body";
    }

    private Output createOutput(Input input, RelayMap relayMap, Map<String, List<USPhoneNumber>> validAndInvalidPhones) {
        Output output = new Output();
        output.setMessage(input.getMessage());
        output.setRoutes(createRoutesFromMap(relayMap));

        output.setInvalidNumbers(getInvalidsAsStrings(validAndInvalidPhones));
        return output;
    }

    private List<String> getInvalidsAsStrings(Map<String, List<USPhoneNumber>> validAndInvalidPhones) {
        List<USPhoneNumber> invalids = validAndInvalidPhones.get("invalid");
        List<String> phonesAsStrings = new ArrayList<String>();
        for(USPhoneNumber currPhone : invalids){
            phonesAsStrings.add(currPhone.toString());
        }

        return phonesAsStrings;
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


    private Map<String, List<USPhoneNumber>> parsePhoneNumbers(Input input) {
        Map<String, List<USPhoneNumber>> validAndInvalidPhones = new HashMap<String, List<USPhoneNumber>>();
        List<USPhoneNumber> validPhonesList = new ArrayList<USPhoneNumber>();
        List<USPhoneNumber> invalidPhonesList = new ArrayList<USPhoneNumber>();

        for(String inputPhoneNumber : input.getRecipients()){
            USPhoneNumber phone = new USPhoneNumber(inputPhoneNumber);
            if(USPhoneNumber.isValid(inputPhoneNumber)){
                validPhonesList.add(phone);
            }else{
                invalidPhonesList.add(phone);
            }
        }

        validAndInvalidPhones.put("valid",validPhonesList);
        validAndInvalidPhones.put("invalid", invalidPhonesList);

        return validAndInvalidPhones;
    }

}
