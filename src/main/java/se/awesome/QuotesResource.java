package se.awesome;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//http://localhost:8080/crazy-quotes/quotes

@Path("/quotes")
public class QuotesResource {
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt() {
        return Response.status(200).header("Access-Control-Allow-Origin", "*")
        		.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
        		.entity("test") 
        		.build(); 
    }
}
