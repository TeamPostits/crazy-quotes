package se.awesome.storage;

public interface UserRepository {
	
	public boolean login(String username, String password);
	
	public boolean containsKey(int key);
	
	public boolean containsUser(String username, String password);

}
