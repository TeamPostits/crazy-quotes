package se.awesome.resource;

import java.util.UUID;

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
import se.awesome.data.LoginData;
import se.awesome.encrypter.Encrypter;
import se.awesome.service.QuotesService;
import se.awesome.storage.mysql.MySQLQuotesRepository;
import se.awesome.storage.mysql.MySQLTokensRepository;
import se.awesome.storage.mysql.MySQLUserRepository;

@Path("/user")
public class UsersResource {
	private final MySQLQuotesRepository sqlQuotesRepository = new MySQLQuotesRepository();
	private final MySQLUserRepository sqlUserRepository = new MySQLUserRepository();
	private final MySQLTokensRepository sqlTokensRepository = new MySQLTokensRepository();
	private final QuotesService quotesService = new QuotesService(
			sqlUserRepository, sqlQuotesRepository, sqlTokensRepository);
	private final Authenticator authenticator = new Authenticator(quotesService);

	@POST
	@Path("/login")
	@Consumes({ "application/xml", "application/json" })
	public Response login(@Context HttpHeaders headers, LoginData data) {

		if (!data.username.equals("") && data.password.length() >= 8) {

			String dbToken = authenticator.login(data.username, data.password);

			if (!dbToken.equals(null)) {
				return Response.status(Status.OK)
						.header("content-type", "application/json")
						.header("X-Auth-Token", dbToken).entity("null").build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/create")
	@Consumes({ "application/xml", "application/json" })
	public Response createUser(@Context HttpHeaders headers, LoginData data) {

		if (!data.username.equals("") && data.password.length() >= 8) {
			
			String salt = UUID.randomUUID().toString();
			String encryptedPassword = Encrypter.getSecurePassword(data.password, salt);

			quotesService.createUser(data.username, encryptedPassword, salt);
			
			String authToken = UUID.randomUUID().toString();
			quotesService.createToken(authToken, data.username);
			
				return Response.status(Status.CREATED)
						.header("content-type", "application/json").header("X-Auth-Token", authToken).build();
			
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	

}
