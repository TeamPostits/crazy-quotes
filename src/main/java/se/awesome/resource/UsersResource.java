package se.awesome.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.awesome.authenticator.Authenticator;
import se.awesome.service.QuotesService;
import se.awesome.storage.mysql.MySQLKeysRepository;
import se.awesome.storage.mysql.MySQLQuotesRepository;
import se.awesome.storage.mysql.MySQLTokensRepository;
import se.awesome.storage.mysql.MySQLUserRepository;

@Path("/login")
public class UsersResource {
	private final MySQLQuotesRepository sqlQuotesRepository = new MySQLQuotesRepository();
	private final MySQLUserRepository sqlUserRepository = new MySQLUserRepository();
	private final MySQLKeysRepository sqlKeysRepository = new MySQLKeysRepository();
	private final MySQLTokensRepository sqlTokensRepository = new MySQLTokensRepository();
	private final QuotesService quotesService = new QuotesService(
			sqlUserRepository, sqlQuotesRepository, sqlKeysRepository,
			sqlTokensRepository);
	private final Authenticator authenticator = new Authenticator(quotesService);

	@POST
	//@Consumes({ "application/xml", "application/json" })
	public Response login(@Context Request request) {
		if (quotesService.isLoggedIn("isabella")) {
			return Response.status(Status.OK).build();
		}else{
			String token = authenticator.login(123, "isabella", "admin2");
			System.out.println("Token: " + token);

			if (!token.equals("")) {
				return Response.status(Status.OK).entity(token).build();
			}
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

}
