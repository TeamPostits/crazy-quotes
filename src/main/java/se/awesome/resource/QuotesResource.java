package se.awesome.resource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import se.awesome.authenticator.Authenticator;
import se.awesome.data.Quote;
import se.awesome.service.QuotesService;
import se.awesome.storage.mysql.MySQLQuotesRepository;
import se.awesome.storage.mysql.MySQLTokensRepository;
import se.awesome.storage.mysql.MySQLUserRepository;

//http://localhost:8080/crazy-quotes/quotes

@Path("/quotes")
public class QuotesResource {

	private static final MySQLQuotesRepository sqlQuotesRepository = new MySQLQuotesRepository();
	private static final MySQLUserRepository sqlUserRepository = new MySQLUserRepository();
	private static final MySQLTokensRepository sqlTokensRepository = new MySQLTokensRepository();
	private static final QuotesService quotesService = new QuotesService(
			sqlUserRepository, sqlQuotesRepository,
			sqlTokensRepository);
	private static final Authenticator authenticator = new Authenticator(quotesService);
	private Thread thread;
	

	@GET
	public Response readQuotes(@Context HttpHeaders headers) {
		String token = headers.getHeaderString("X-Auth-Token");
		String username = headers.getHeaderString("X-Username");

		if (authenticator.isAuthTokenValid( token,
				username)) {

			Iterator<Quote> quotes = quotesService.readQuotes().iterator();
			JsonArray jsonArray = new JsonArray();
			Quote quote;
			while (quotes.hasNext()) {
				JsonObject json = new JsonObject();
				quote = quotes.next();
				json.addProperty("text", quote.getText());
				json.addProperty("author", quote.getAuthor());
				json.addProperty("year", quote.getYear());
				json.addProperty("createdBy", quote.getCreatedBy());
				jsonArray.add(json);
			}

			return Response.status(Status.OK).entity(jsonArray.toString())
					.build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@POST
	public Response createQuote(@Context HttpHeaders headers, Quote quote) {
		String token = headers.getHeaderString("X-Auth-Token");
		String username = headers.getHeaderString("X-Username");
		System.out.println("Entered");

		if (authenticator.isAuthTokenValid(
				token, username)) {
			quotesService.createQuote(quote);
			System.out.println("got this far");
			return Response.status(Status.CREATED).header("X-Auth-Token", token).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

}
