package se.awesome.service;

import java.util.List;

import se.awesome.data.Quote;
import se.awesome.storage.KeysRepository;
import se.awesome.storage.QuotesRepository;
import se.awesome.storage.TokensRepository;
import se.awesome.storage.UserRepository;

public class QuotesService {
	
	private UserRepository userRepository;
	private QuotesRepository quotesRepository;
	private KeysRepository keysRepository;
	private TokensRepository tokenRepository;
	
	public QuotesService(UserRepository userRepository, QuotesRepository quotesRepository, KeysRepository keysRepository, TokensRepository tokensRepository){
		this.userRepository = userRepository;
		this.quotesRepository = quotesRepository;
		this.keysRepository = keysRepository;
		tokenRepository = tokensRepository;
		
	}
	
	public void login(String username, String password){
		userRepository.login(username, password);
	}
	
	public boolean containsKey(int key){
		return userRepository.containsKey(key);
	}
	
	public boolean containsUser(int key, String username, String password){
		return keysRepository.containsUser(key, username) && userRepository.containsUser(username, password);
	}
	
	public boolean createToken(String token, String username){
		return tokenRepository.createToken(token, username);
	}
	
	public boolean containsToken(String token, String username){
		return tokenRepository.containsToken(token, username);
	}
	
	public boolean isLoggedIn(String username){
		return tokenRepository.isLoggedIn(username);
	}
	
	public void createQuote(Quote quote){
		quotesRepository.createQuote(quote);
	}
	
	public List<Quote> readQuotes(){
		return quotesRepository.readQuotes();
	}

}
