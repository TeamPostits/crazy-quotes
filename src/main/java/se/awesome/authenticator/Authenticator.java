package se.awesome.authenticator;

import java.util.UUID;

import se.awesome.service.QuotesService;
import se.awesome.storage.UserRepository;

public class Authenticator {

	private QuotesService quotesService;
	
	public Authenticator(QuotesService quotesService){
		this.quotesService = quotesService;	
	}
	
	public String login(int serviceKey, String username, String password){
		if(quotesService.containsKey(serviceKey)){
			if(quotesService.containsUser(serviceKey, username, password)){
				String authToken = UUID.randomUUID().toString();
                quotesService.createToken(authToken, username);
                return authToken;
				
			}
		}
		return "";
	}
	
	public boolean isAuthTokenValid(int serviceKey, String token, String username){
		if(quotesService.containsKey(serviceKey)){
			if(quotesService.containsToken(token, username)){
				return true;
			}
		}
		return false;
	}
   
}
