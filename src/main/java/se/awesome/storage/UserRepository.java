package se.awesome.storage;

public interface UserRepository {
	
	public boolean createUser(String username, String password, String salt);
	
	public boolean containsUser(String username, String password);
	
	public String getPassword(String username);

	public String getSalt(String username);

}
