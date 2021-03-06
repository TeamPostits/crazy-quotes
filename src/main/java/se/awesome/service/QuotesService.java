package se.awesome.service;

import java.util.List;

import se.awesome.data.Quote;
import se.awesome.storage.QuotesRepository;
import se.awesome.storage.TokensRepository;
import se.awesome.storage.UserRepository;

public class QuotesService {
	
	private UserRepository userRepository;
	private QuotesRepository quotesRepository;
	private TokensRepository tokenRepository;
	
	public QuotesService(UserRepository userRepository, QuotesRepository quotesRepository, TokensRepository tokensRepository){
		this.userRepository = userRepository;
		this.quotesRepository = quotesRepository;
		tokenRepository = tokensRepository;
		
	}
	
	public boolean createUser(String username, String password, String salt){
		return userRepository.createUser(username, password, salt);
	}
	
	public boolean containsUser(String username, String password){
		return userRepository.containsUser(username, password);
	}
	
	public String getSalt(String username){
		return userRepository.getSalt(username);
	}
	
	public String getPassword(String username){
		return userRepository.getPassword(username);
	}
	
	public boolean createToken(String token, String username){
		return tokenRepository.createToken(token, username);
	}
	
	public boolean containsToken(String token, String username){
		return tokenRepository.containsToken(token, username);
	}
	
	public void createQuote(Quote quote){
		quotesRepository.createQuote(quote);
	}
	
	public List<Quote> readQuotes(){
		return quotesRepository.readQuotes();
	}

}
