package se.awesome.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.awesome.authenticator.Authenticator;
import se.awesome.service.QuotesService;
import se.awesome.storage.mysql.MySQLQuotesRepository;
import se.awesome.storage.mysql.MySQLTokensRepository;
import se.awesome.storage.mysql.MySQLUserRepository;

@Path("/login")
public class UsersResource {
	private final MySQLQuotesRepository sqlQuotesRepository = new MySQLQuotesRepository();
	private final MySQLUserRepository sqlUserRepository = new MySQLUserRepository();
	private final MySQLTokensRepository sqlTokensRepository = new MySQLTokensRepository();
	private final QuotesService quotesService = new QuotesService(
			sqlUserRepository, sqlQuotesRepository,
			sqlTokensRepository);
	private final Authenticator authenticator = new Authenticator(quotesService);

	@POST
	//@Consumes({ "application/xml", "application/json" })
	public Response login(@Context HttpHeaders headers,String body) {
		
		System.out.println(body);
		
			String dbToken = authenticator.login("isabella", "admin2");

			if (!dbToken.equals("")) {
				return Response.status(Status.OK).header("content-type", "application/json").header("X-Auth-Token", dbToken).entity("null").build();
			}
			return Response.status(Status.UNAUTHORIZED).build();
	}

}
