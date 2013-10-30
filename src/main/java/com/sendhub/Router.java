package com.sendhub;

import javax.ws.rs.*;

@Path("/route")
public class Router {
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public void routes(Input input){
        System.out.println(input);
    }

}
