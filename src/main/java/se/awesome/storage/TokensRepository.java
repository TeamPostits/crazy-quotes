package se.awesome.storage;

public interface TokensRepository {
	
	public boolean createToken(String token, String username);
	
	public boolean containsToken(String token, String username);
	

}
