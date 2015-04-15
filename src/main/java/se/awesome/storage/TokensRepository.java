package se.awesome.storage;

public interface TokensRepository {
	
	public boolean containsToken(String token, String username);
	
	public boolean createToken(String token, String username);
	
	public boolean isLoggedIn(String username);

}
