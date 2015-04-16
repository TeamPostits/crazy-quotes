package se.awesome.authenticator;

import java.util.UUID;

import se.awesome.service.QuotesService;
import se.awesome.storage.UserRepository;

public class Authenticator {

	private QuotesService quotesService;
	
	public Authenticator(QuotesService quotesService){
		this.quotesService = quotesService;	
	}
	
	public String login(String username, String password){
			if(quotesService.containsUser(username, password)){
				String authToken = UUID.randomUUID().toString();
                quotesService.createToken(authToken, username);
                return authToken;
				
			}
		return "";
	}
	
	public boolean isAuthTokenValid(String token, String username){
			if(quotesService.containsToken(token, username)){
				return true;
			}
		return false;
	}
   
}
