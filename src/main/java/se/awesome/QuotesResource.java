package se.awesome;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//http://localhost:8080/crazy-quotes/quotes

@Path("/quotes")
public class QuotesResource {
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}
